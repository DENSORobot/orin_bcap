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
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using ORiN2.Library;

namespace ORiN2.bCAP
{
    class bCAPConnectionTCP : bCAPConnectionBase
    {
        private TcpClient m_tcpStream;

        private bCAPPacketConverter m_conv;
        private object m_lock;

        public bCAPConnectionTCP()
        {

        }

        public override void Connect(ConnOptEther optEth, int iTimeout, int iRetry)
        {
            try
            {
                m_tcpStream = new TcpClient();
                var iar = m_tcpStream.BeginConnect(optEth.DstAddr, optEth.DstPort, null, null);
                if (!iar.IsCompleted)
                {
                    if (!iar.AsyncWaitHandle.WaitOne(iTimeout))
                    {
                        try
                        {
                            m_tcpStream.Close();
                            m_tcpStream.EndConnect(iar);
                        }
                        catch (Exception) { }
                        throw new TimeoutException();
                    }
                }
                m_tcpStream.EndConnect(iar);

                m_tcpStream.NoDelay = true;
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
            if (m_tcpStream.Connected)
            {
                m_tcpStream.GetStream().Dispose();
                m_tcpStream.Close();
            }
            base.Dispose();
        }

        public override void SetTimeout(int iTimeout)
        {
            m_tcpStream.SendTimeout = iTimeout;
            m_tcpStream.ReceiveTimeout = iTimeout;
            base.SetTimeout(iTimeout);
        }

        public override void SetRetry(int iRetry)
        {
            base.SetRetry(1);
        }

        public override void SendPacket(bCAPPacket send)
        {
            byte[] bySend = m_conv.Serialize(send);
            m_tcpStream.GetStream().Write(bySend, 0, bySend.Length);
        }

        public override bCAPPacket RecvPacket(UInt16 iSerial)
        {
            byte[] byRecv;
            var recv = new bCAPPacket();

            using (var ms = new MemoryStream())
            {
                var br = new BinaryReader(m_tcpStream.GetStream());

                while (true)
                {
                    try
                    {
                        // ストリームを初期化
                        ms.Position = 0;
                        ms.SetLength(0);

                        // SOH
                        ms.WriteByte(br.ReadByte());

                        // Message Length
                        byRecv = br.ReadBytes(4);
                        ms.Write(byRecv, 0, byRecv.Length);

                        // 残りを受信
                        byRecv = br.ReadBytes((Int32)bCAPByteConverter.Byte2Value(byRecv, typeof(Int32)) - 5);
                        ms.Write(byRecv, 0, byRecv.Length);

                        // 受信パケットが送信パケットの応答であり、かつ、S_EXECUTINGでなければ終わり
                        recv = m_conv.Deserialize(ms.ToArray());
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
                send.iReserv = 0;

                // パケット送信
                SendPacket(send);

                // シリアル番号を更新
                m_iSerial = (UInt16)((m_iSerial != UInt16.MaxValue) ? (m_iSerial + 1) : 1);

                // パケットを受信
                recv = RecvPacket(send.iSerial);
            }

            if (recv.iFuncID < 0)
            {
                throw new ORiN2Exception(recv.iFuncID, null);
            }

            return recv;
        }
    }
}
