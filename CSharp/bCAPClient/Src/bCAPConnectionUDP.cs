/**
 * Software License Agreement (MIT License)
 *
 * @copyright Copyright (c) 2015 DENSO WAVE INCORPORATED
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using ORiN2.Library;

namespace ORiN2.bCAP
{
    class bCAPConnectionUDP : bCAPConnectionBase
    {
        private const int RETRY_MIN = 1;
        private const int RETRY_MAX = 7;

        private IPEndPoint m_ipEpSend;
        private UdpClient m_udpStream;

        private bCAPPacketConverter m_conv;
        private object m_lock;

        public bCAPConnectionUDP()
        {

        }

        public override void Connect(ConnOptEther optEth, int iTimeout, int iRetry)
        {
            try
            {
                m_ipEpSend = new IPEndPoint(IPAddress.Parse(optEth.DstAddr), optEth.DstPort);
                m_udpStream = new UdpClient(
                    new IPEndPoint(IPAddress.Parse(optEth.SrcAddr), optEth.SrcPort)
                );
                SetTimeout(iTimeout);
                SetRetry(iRetry);

                m_conv = new bCAPPacketConverter();
                m_lock = new object();
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public override void Dispose()
        {
            m_udpStream.Close();
            base.Dispose();
        }

        public override void SetTimeout(int iTimeout)
        {
            m_udpStream.Client.SendTimeout = iTimeout;
            m_udpStream.Client.ReceiveTimeout = iTimeout;
            base.SetTimeout(iTimeout);
        }

        public override void SetRetry(int iRetry)
        {
            if (RETRY_MIN > iRetry)
            {
                base.SetRetry(RETRY_MIN);
            }
            else if (RETRY_MAX < iRetry)
            {
                base.SetRetry(RETRY_MAX);
            }
            else // RETRY_MIN <= iRetry <= RETRY_MAX
            {
                base.SetRetry(iRetry);
            }
        }

        public override void SendPacket(bCAPPacket send)
        {
            byte[] bySend = m_conv.Serialize(send);
            m_udpStream.Send(bySend, bySend.Length, m_ipEpSend);
        }

        public override bCAPPacket RecvPacket(UInt16 iSerial)
        {
            byte[] byRecv;
            var recv = new bCAPPacket();
            var ipEpRecv = new IPEndPoint(IPAddress.Any, 0);

            while (true)
            {
                try
                {
                    // データグラム受信
                    byRecv = m_udpStream.Receive(ref ipEpRecv);
                    if (!ipEpRecv.Equals(m_ipEpSend))
                    {
                        continue;
                    }

                    // 受信パケットが送信パケットの応答であり、かつ、S_EXECUTINGでなければ終わり
                    recv = m_conv.Deserialize(byRecv);
                    if ((recv.iSerial == iSerial) && (recv.iFuncID != HResult.S_EXECUTING))
                    {
                        break;
                    }
                }
                catch (Exception ex)
                {
                    throw ex;
                }
            }

            return recv;
        }

        public override bCAPPacket SendAndRecv(bCAPPacket send)
        {
            var recv = new bCAPPacket();

            lock (m_lock)
            {
                // シリアル番号を設定
                send.iSerial = m_iSerial;
                send.iReserv = m_iSerial;

                int iRetry = 0;
            retry_proc:
                try
                {
                    // シリアル番号を設定（リトライ）
                    send.iSerial = m_iSerial;

                    // パケットを送信
                    SendPacket(send);

                    // シリアル番号を更新
                    m_iSerial = (UInt16)((m_iSerial != UInt16.MaxValue) ? (m_iSerial + 1) : 1);

                    // パケットを受信
                    recv = RecvPacket(send.iSerial);
                }
                catch (Exception ex)
                {
                    // リトライ回数を超過するまで繰り返す
                    iRetry++;
                    if (iRetry < m_iRetry) goto retry_proc;

                    // 例外をスロー
                    throw ex;
                }
            }

            if (recv.iFuncID < 0)
            {
                throw new ORiN2Exception(recv.iFuncID, null);
            }

            return recv;
        }
    }
}
