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
using System.Text;
using System.Threading.Tasks;
using ORiN2.Library;

namespace ORiN2.bCAP
{
    abstract class bCAPConnectionBase : IDisposable
    {
        protected ushort m_iSerial = 1;
        protected int m_iTimeout = 0;
        protected int m_iRetry = 0;

        public virtual void Connect(
            ConnOptEther optEth, int iTimeout, int iRetry) { }

        public virtual void Dispose() { }

        public virtual int GetTimeout()
        {
            return m_iTimeout;
        }

        public virtual void SetTimeout(int iTimeout)
        {
            m_iTimeout = iTimeout;
        }

        public virtual int GetRetry()
        {
            return m_iRetry;
        }

        public virtual void SetRetry(int iRetry)
        {
            m_iRetry = iRetry;
        }

        public abstract void SendPacket(bCAPPacket send);
        public abstract bCAPPacket RecvPacket(UInt16 iSerial);
        public abstract bCAPPacket SendAndRecv(bCAPPacket send);
    }
}
