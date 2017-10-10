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

namespace ORiN2.bCAP
{
    static class bCAPByteConverter
    {
        private static Dictionary<Type, Func<byte[], object>> m_byt2val;
        private static Dictionary<Type, Func<object, byte[]>> m_val2byt;

        static bCAPByteConverter()
        {
            m_byt2val = new Dictionary<Type, Func<byte[], object>>();
            m_byt2val.Add(typeof(Int16), Byte2Short);
            m_byt2val.Add(typeof(Int32), Byte2Int);
            m_byt2val.Add(typeof(Single), Byte2Single);
            m_byt2val.Add(typeof(Double), Byte2Double);
            m_byt2val.Add(typeof(Decimal), Byte2CY);
            m_byt2val.Add(typeof(DateTime), Byte2Date);
            m_byt2val.Add(typeof(String), Byte2String);
            m_byt2val.Add(typeof(Boolean), Byte2Bool);
            m_byt2val.Add(typeof(UInt16), Byte2UShort);
            m_byt2val.Add(typeof(UInt32), Byte2UInt);
            m_byt2val.Add(typeof(Int64), Byte2Long);
            m_byt2val.Add(typeof(UInt64), Byte2ULong);

            m_val2byt = new Dictionary<Type, Func<object, byte[]>>();
            m_val2byt.Add(typeof(Int16), Short2Byte);
            m_val2byt.Add(typeof(Int32), Int2Byte);
            m_val2byt.Add(typeof(Single), Single2Byte);
            m_val2byt.Add(typeof(Double), Double2Byte);
            m_val2byt.Add(typeof(Decimal), CY2Byte);
            m_val2byt.Add(typeof(DateTime), Date2Byte);
            m_val2byt.Add(typeof(String), String2Byte);
            m_val2byt.Add(typeof(Boolean), Bool2Byte);
            m_val2byt.Add(typeof(UInt16), UShort2Byte);
            m_val2byt.Add(typeof(UInt32), UInt2Byte);
            m_val2byt.Add(typeof(Int64), Long2Byte);
            m_val2byt.Add(typeof(UInt64), ULong2Byte);
        }
        
        /* byte[] => object */
        public static object Byte2Value(byte[] arg, Type t){
            return m_byt2val[t](arg);
        }

        /* object => byte[] */
        public static byte[] Value2Byte(object arg)
        {
            return m_val2byt[arg.GetType()](arg);
        }

        /* byte[] => Int16 */
        private static object Byte2Short(byte[] arg)
        {
            bCAPEndian(arg);
            return BitConverter.ToInt16(arg, 0);
        }

        /* Int16 => byte[] */
        private static byte[] Short2Byte(object arg)
        {
            byte[] bRet = BitConverter.GetBytes((Int16)arg);
            bCAPEndian(bRet);
            return bRet;
        }

        /* byte[] => Int32 */
        private static object Byte2Int(byte[] arg)
        {
            bCAPEndian(arg);
            return BitConverter.ToInt32(arg, 0);
        }

        /* Int32 => byte[] */
        private static byte[] Int2Byte(object arg)
        {
            byte[] bRet = BitConverter.GetBytes((Int32)arg);
            bCAPEndian(bRet);
            return bRet;
        }

        /* byte[] => Single */
        private static object Byte2Single(byte[] arg)
        {
            bCAPEndian(arg);
            return BitConverter.ToSingle(arg, 0);
        }

        /* Single => byte[] */
        private static byte[] Single2Byte(object arg)
        {
            byte[] bRet = BitConverter.GetBytes((Single)arg);
            bCAPEndian(bRet);
            return bRet;
        }

        /* byte[] => Double */
        private static object Byte2Double(byte[] arg)
        {
            bCAPEndian(arg);
            return BitConverter.ToDouble(arg, 0);
        }

        /* Double => byte[] */
        private static byte[] Double2Byte(object arg)
        {
            byte[] bRet = BitConverter.GetBytes((Double)arg);
            bCAPEndian(bRet);
            return bRet;
        }

        /* byte[] => Decimal */
        private static object Byte2CY(byte[] arg)
        {
            bCAPEndian(arg);
            return new Decimal(BitConverter.ToInt64(arg, 0));
        }

        /* Decimal => byte[] */
        private static byte[] CY2Byte(object arg)
        {
            byte[] bRet = BitConverter.GetBytes((Int64)(Decimal)arg);
            bCAPEndian(bRet);
            return bRet;
        }

        /* byte[] => Date */
        private static object Byte2Date(byte[] arg)
        {
            bCAPEndian(arg);
            return DateTime.FromOADate(BitConverter.ToDouble(arg, 0));
        }

        /* Date => byte[] */
        private static byte[] Date2Byte(object arg)
        {
            byte[] bRet = BitConverter.GetBytes(((DateTime)arg).ToOADate());
            bCAPEndian(bRet);
            return bRet;
        }

        /* byte[] => String */
        private static object Byte2String(byte[] arg)
        {
            return Encoding.Unicode.GetString(arg);
        }

        /* String => byte[] */
        private static byte[] String2Byte(object arg)
        {
            return Encoding.Unicode.GetBytes((String)arg);
        }

        /* byte[] => Boolean */
        private static object Byte2Bool(byte[] arg)
        {
            return (BitConverter.ToInt16(arg, 0) != 0);
        }

        /* Boolean => byte[] */
        private static byte[] Bool2Byte(object arg)
        {
            return BitConverter.GetBytes((Int16)((Boolean)arg ? -1 : 0));
        }

        /* byte[] => UInt16 */
        private static object Byte2UShort(byte[] arg)
        {
            bCAPEndian(arg);
            return BitConverter.ToUInt16(arg, 0);
        }

        /* UInt16 => byte[] */
        private static byte[] UShort2Byte(object arg)
        {
            byte[] bRet = BitConverter.GetBytes((UInt16)arg);
            bCAPEndian(bRet);
            return bRet;
        }

        /* byte[] => UInt32 */
        private static object Byte2UInt(byte[] arg)
        {
            bCAPEndian(arg);
            return BitConverter.ToUInt32(arg, 0);
        }

        /* UInt32 => byte[] */
        private static byte[] UInt2Byte(object arg)
        {
            byte[] bRet = BitConverter.GetBytes((UInt32)arg);
            bCAPEndian(bRet);
            return bRet;
        }

        /* byte[] => Int64 */
        private static object Byte2Long(byte[] arg)
        {
            bCAPEndian(arg);
            return BitConverter.ToInt64(arg, 0);
        }
        
        /* Int64 => byte[] */
        private static byte[] Long2Byte(object arg)
        {
            byte[] bRet = BitConverter.GetBytes((Int64)arg);
            bCAPEndian(bRet);
            return bRet;
        }

        /* byte[] => UInt64 */
        private static object Byte2ULong(byte[] arg)
        {
            bCAPEndian(arg);
            return BitConverter.ToUInt64(arg, 0);
        }

        /* UInt64 => byte[] */
        private static byte[] ULong2Byte(object arg)
        {
            byte[] bRet = BitConverter.GetBytes((UInt64)arg);
            bCAPEndian(bRet);
            return bRet;
        }

        /* bCAP Endian <=> PC Endian */
        private static void bCAPEndian(byte[] arg)
        {
            if (!BitConverter.IsLittleEndian)
            {
                Array.Reverse(arg);
            }
        }
    }
}
