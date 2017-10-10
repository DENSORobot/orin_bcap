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
using System.Threading.Tasks;

namespace ORiN2.Library
{
    public static class HResult
    {
        // CAO
        public const int S_OK = unchecked((int)0x00000000);
        public const int S_FALSE = unchecked((int)0x00000001);
        public const int E_UNEXPECTED = unchecked((int)0x8000FFFF);
        public const int E_NOTIMPL = unchecked((int)0x80004001);
        public const int E_OUTOFMEMORY = unchecked((int)0x8007000E);
        public const int E_INVALIDARG = unchecked((int)0x80070057);
        public const int E_POINTER = unchecked((int)0x80004003);
        public const int E_HANDLE = unchecked((int)0x80070006);
        public const int E_ABORT = unchecked((int)0x80004004);
        public const int E_FAIL = unchecked((int)0x80004005);
        public const int E_ACCESSDENIED = unchecked((int)0x80070005);
        public const int E_WINDOWS_MASK = unchecked((int)0x8007FFFF);
        public const int E_CAO_SEM_CREATE = unchecked((int)0x80000200);
        public const int E_CAO_PROV_INVALID = unchecked((int)0x80000201);
        public const int E_CAO_COMPUTER_NAME = unchecked((int)0x80000202);
        public const int E_CAO_VARIANT_TYPE_NOSUPPORT = unchecked((int)0x80000203);
        public const int E_CAO_OBJECT_NOTFOUND = unchecked((int)0x80000204);
        public const int E_CAO_COLLECTION_REGISTERED = unchecked((int)0x80000205);
        public const int E_CAO_THREAD_CREATE = unchecked((int)0x80000207);
        public const int E_CAO_REMOTE_ENGINE = unchecked((int)0x80000208);
        public const int E_CAO_REMOTE_PROVIDER = unchecked((int)0x80000209);
        public const int E_CAO_NOT_WRITABLE = unchecked((int)0x8000020a);
        public const int E_CAO_CMD_EXECUTE = unchecked((int)0x8000020b);
        public const int E_CAO_PROV_NO_LICENSE = unchecked((int)0x8000020c);
        public const int E_CAO_PRELOAD = unchecked((int)0x8000020d);
        public const int E_CRDIMPL = unchecked((int)0x80000400);
        public const int E_CAOP_SYSTEMNAME_INVALID = unchecked((int)0x80000401);
        public const int E_CAOP_SYSTEMTYPE_INVALID = unchecked((int)0x80000402);
        public const int E_CANCEL = unchecked((int)0x80000403);
        public const int E_CAOP_NOT_WRITABLE = unchecked((int)0x80000404);
        public const int E_TIMEOUT = unchecked((int)0x80000900);
        public const int E_NO_LICENSE = unchecked((int)0x80000901);
        public const int E_NOT_CONNECTED = unchecked((int)0x80000902);
        public const int E_NOT_USE = unchecked((int)0x80000903);
        public const int E_INVALID_CMD_NAME = unchecked((int)0x80000904);
        public const int E_MAX_OBJECT = unchecked((int)0x80000905);
        public const int E_WINSOCK_MASK = unchecked((int)0x8091FFFF);
        // b-CAP
        public const int S_EXECUTING = unchecked((int)0x00000900);
        public const int E_INVALIDPACKET = unchecked((int)0x80010000);
    }

    public class ORiN2Exception : Exception
    {
        private static readonly Dictionary<int, string> m_hr2desc;

        static ORiN2Exception()
        {
            m_hr2desc = new Dictionary<int, string>();
        }

        static string HResult2Description(int hr)
        {
            string desc = "(0x" + hr.ToString("X8") + ")";

            try
            {
                desc = m_hr2desc[hr] + desc;
            }
            catch (Exception) { }

            return desc;
        }

        public ORiN2Exception(int hr, Exception ex)
            : base(ORiN2Exception.HResult2Description(hr), ex)
        {
            base.HResult = hr;
        }
    }
}
