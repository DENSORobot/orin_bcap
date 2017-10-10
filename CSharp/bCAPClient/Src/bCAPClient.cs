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
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using ORiN2.Library;

namespace ORiN2.bCAP
{
    public partial class bCAPClient : IDisposable
    {
        private bCAPConnectionBase m_conn;

        public bCAPClient(string strConn, int iTimeout, int iRetry)
        {
            if (strConn.Length < 3) throw new ArgumentException();

            string strOpt = strConn.Substring(0, 3).ToLower();
            switch (strOpt)
            {
                case "tcp":
                    m_conn = new bCAPConnectionTCP();
                    break;
                case "udp":
                    m_conn = new bCAPConnectionUDP();
                    break;
                default:
                    throw new ArgumentException();
            }

            // パラメータ解析
            var optEth = ConnOptParser.ParseEtherOption(strConn);

            // 接続
            m_conn.Connect(optEth, iTimeout, iRetry);
        }

        public void Dispose()
        {
            if (m_conn != null)
            {
                m_conn.Dispose();
                m_conn = null;
            }
        }

        public int GetTimeout()
        {
            int retval = -1;
            if (m_conn != null)
            {
                retval = m_conn.GetTimeout();
            }
            return retval;
        }

        public void SetTimeout(int iTimeout)
        {
            if (m_conn != null)
            {
                m_conn.SetTimeout(iTimeout);
            }
        }

        public int GetRetry()
        {
            int retval = -1;
            if (m_conn != null)
            {
                retval = m_conn.GetRetry();
            }
            return retval;
        }

        public void SetRetry(int iRetry)
        {
            if (m_conn != null)
            {
                m_conn.SetRetry(iRetry);
            }
        }
    }
}
