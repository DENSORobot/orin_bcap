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

package orin2.library;

public class HResult {
    // CAO
    public static final int S_OK = 0x00000000;
    public static final int S_FALSE = 0x00000001;
    public static final int E_UNEXPECTED = 0x8000FFFF;
    public static final int E_NOTIMPL = 0x80004001;
    public static final int E_OUTOFMEMORY = 0x8007000E;
    public static final int E_INVALIDARG = 0x80070057;
    public static final int E_POINTER = 0x80004003;
    public static final int E_HANDLE = 0x80070006;
    public static final int E_ABORT = 0x80004004;
    public static final int E_FAIL = 0x80004005;
    public static final int E_ACCESSDENIED = 0x80070005;
    public static final int E_WINDOWS_MASK = 0x8007FFFF;
    public static final int E_CAO_SEM_CREATE = 0x80000200;
    public static final int E_CAO_PROV_INVALID = 0x80000201;
    public static final int E_CAO_COMPUTER_NAME = 0x80000202;
    public static final int E_CAO_VARIANT_TYPE_NOSUPPORT = 0x80000203;
    public static final int E_CAO_OBJECT_NOTFOUND = 0x80000204;
    public static final int E_CAO_COLLECTION_REGISTERED = 0x80000205;
    public static final int E_CAO_THREAD_CREATE = 0x80000207;
    public static final int E_CAO_REMOTE_ENGINE = 0x80000208;
    public static final int E_CAO_REMOTE_PROVIDER = 0x80000209;
    public static final int E_CAO_NOT_WRITABLE = 0x8000020a;
    public static final int E_CAO_CMD_EXECUTE = 0x8000020b;
    public static final int E_CAO_PROV_NO_LICENSE = 0x8000020c;
    public static final int E_CAO_PRELOAD = 0x8000020d;
    public static final int E_CRDIMPL = 0x80000400;
    public static final int E_CAOP_SYSTEMNAME_INVALID = 0x80000401;
    public static final int E_CAOP_SYSTEMTYPE_INVALID = 0x80000402;
    public static final int E_CANCEL = 0x80000403;
    public static final int E_CAOP_NOT_WRITABLE = 0x80000404;
    public static final int E_TIMEOUT = 0x80000900;
    public static final int E_NO_LICENSE = 0x80000901;
    public static final int E_NOT_CONNECTED = 0x80000902;
    public static final int E_NOT_USE = 0x80000903;
    public static final int E_INVALID_CMD_NAME = 0x80000904;
    public static final int E_MAX_OBJECT = 0x80000905;
    public static final int E_WINSOCK_MASK = 0x8091FFFF;
    // b-CAP
    public static final int S_EXECUTING = 0x00000900;
    public static final int E_INVALIDPACKET = 0x80010000;
    
    public static boolean SUCCEEDED(int hr) {
    	return hr >= 0;
    }
    
    public static boolean FAILED(int hr) {
    	return hr < 0;
    }
}
