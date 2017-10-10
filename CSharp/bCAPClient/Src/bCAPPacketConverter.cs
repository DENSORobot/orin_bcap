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
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.IO;
using System.Text;
using ORiN2.Library;

namespace ORiN2.bCAP
{
    enum VARENUM : short
    {
        VT_EMPTY = 0,
        VT_NULL = 1,
        VT_I2 = 2,
        VT_I4 = 3,
        VT_R4 = 4,
        VT_R8 = 5,
        VT_CY = 6,
        VT_DATE = 7,
        VT_BSTR = 8,
        VT_ERROR = 10,
        VT_BOOL = 11,
        VT_VARIANT = 12,
        VT_UI1 = 17,
        VT_UI2 = 18,
        VT_UI4 = 19,
        VT_I8 = 20,
        VT_UI8 = 21,
        VT_ARRAY = 0x2000,
    }
    
    class bCAPPacketConverter
    {
        private const byte BCAP_SOH = 1;
        private const byte BCAP_EOT = 4;

        private static Dictionary<VARENUM, KeyValuePair<Type, int>> m_vt2type;

        static bCAPPacketConverter()
        {
            m_vt2type = new Dictionary<VARENUM, KeyValuePair<Type, int>>();
            m_vt2type[VARENUM.VT_I2] = new KeyValuePair<Type, int>(typeof(Int16), 2);
            m_vt2type[VARENUM.VT_I4] = new KeyValuePair<Type, int>(typeof(Int32), 4);
            m_vt2type[VARENUM.VT_R4] = new KeyValuePair<Type, int>(typeof(Single), 4);
            m_vt2type[VARENUM.VT_R8] = new KeyValuePair<Type, int>(typeof(Double), 8);
            m_vt2type[VARENUM.VT_CY] = new KeyValuePair<Type, int>(typeof(Decimal), 8);
            m_vt2type[VARENUM.VT_DATE] = new KeyValuePair<Type, int>(typeof(DateTime), 8);
            m_vt2type[VARENUM.VT_BSTR] = new KeyValuePair<Type, int>(typeof(String), -1);
            m_vt2type[VARENUM.VT_BOOL] = new KeyValuePair<Type, int>(typeof(Boolean), 2);
            m_vt2type[VARENUM.VT_UI1] = new KeyValuePair<Type, int>(typeof(Byte), 1);
            m_vt2type[VARENUM.VT_UI2] = new KeyValuePair<Type, int>(typeof(UInt16), 2);
            m_vt2type[VARENUM.VT_UI4] = new KeyValuePair<Type, int>(typeof(UInt32), 4);
            m_vt2type[VARENUM.VT_I8] = new KeyValuePair<Type, int>(typeof(Int64), 8);
            m_vt2type[VARENUM.VT_UI8] = new KeyValuePair<Type, int>(typeof(UInt64), 8);
            // 検索避けのためTypeが被る型は後で追加
            m_vt2type[VARENUM.VT_EMPTY] = new KeyValuePair<Type, int>(null, 0);
            m_vt2type[VARENUM.VT_NULL] = new KeyValuePair<Type, int>(null, 0);
            m_vt2type[VARENUM.VT_ERROR] = new KeyValuePair<Type, int>(typeof(Int32), 4);
        }

        /* Typeをキーに型を検索 */
        private static VARENUM FindVtFromType(Type t)
        {
            return m_vt2type.First(e => e.Value.Key == t).Key;
        }

        public byte[] Serialize(bCAPPacket packet)
        {
            byte[] bRet = null;

            try
            {
                using (var ms = new MemoryStream())
                {
                    using (var bw = new BinaryWriter(ms))
                    {
                        // SOH
                        bw.Write(BCAP_SOH);

                        // Message Length（最後に正しい値を入力）
                        bw.Write((Int32)0);

                        // Serial Number
                        bw.Write(bCAPByteConverter.Value2Byte(packet.iSerial));

                        // Reserve Number
                        bw.Write(bCAPByteConverter.Value2Byte(packet.iReserv));

                        // Function ID
                        bw.Write(bCAPByteConverter.Value2Byte(packet.iFuncID));

                        // Number of arguments
                        bw.Write(bCAPByteConverter.Value2Byte((Int16)packet.aryArgs.Count));

                        // Arguments
                        Serialize_ObjectArray(bw, packet.aryArgs, true);

                        // EOH
                        bw.Write(BCAP_EOT);

                        // Message Length（正しい値を入力）
                        bw.BaseStream.Position = 1;
                        bw.Write(bCAPByteConverter.Value2Byte((Int32)ms.Length));
                    }

                    bRet = ms.ToArray();
                }
            }
            catch (Exception ex)
            {
                throw new ORiN2Exception(HResult.E_INVALIDARG, ex);
            }

            return bRet;
        }

        private void Serialize_ObjectArray(BinaryWriter bw, ArrayList args, bool first = false)
        {
            long lStart, lEnd;

            foreach (object obj in args)
            {
                // 変換前のオフセットを記憶
                lStart = bw.BaseStream.Position;

                if (first)
                {
                    // 引数サイズ（最後に正しい値を入力）
                    bw.Write((Int32)0);
                }

                // 引数
                Serialize_Object(bw, obj);

                // 変換後のオフセットを記憶
                lEnd = bw.BaseStream.Position;

                if (first)
                {
                    // 引数サイズ（正しい値を入力）
                    bw.BaseStream.Position = lStart;
                    bw.Write(bCAPByteConverter.Value2Byte((Int32)(lEnd - lStart - 4)));

                    // 最後尾に戻す
                    bw.BaseStream.Position = lEnd;
                }
            }
        }

        private void Serialize_Object(BinaryWriter bw, object obj)
        {
            if (obj == null)
            {
                // 型: VT_EMPTY, 要素数: 1
                bw.Write(bCAPByteConverter.Value2Byte((Int16)VARENUM.VT_EMPTY));
                bw.Write(bCAPByteConverter.Value2Byte((Int32)1));
            }
            else
            {
                // 引数の型を取得
                Type t = obj.GetType();

                if (t.IsArray)
                {
                    // 配列に変換
                    Array objArray = (Array)obj;

                    // 要素の型を取得
                    t = t.GetElementType();

                    if(t == typeof(Object))
                    {
                        // 型: (VT_VARIANT | VT_ARRAY), 要素数: objArray.Length
                        bw.Write(bCAPByteConverter.Value2Byte((Int16)(VARENUM.VT_VARIANT | VARENUM.VT_ARRAY)));
                        bw.Write(bCAPByteConverter.Value2Byte((Int32)objArray.Length));
                        // 再帰
                        Serialize_ObjectArray(bw, new ArrayList(objArray));
                    }
                    else
                    {
                        // TypeをVARENUMに変換
                        VARENUM vt = FindVtFromType(t);

                        // 型: (vt | VT_ARRAY), 要素数: objArray.Length
                        bw.Write(bCAPByteConverter.Value2Byte((Int16)(vt | VARENUM.VT_ARRAY)));
                        bw.Write(bCAPByteConverter.Value2Byte((Int32)objArray.Length));

                        if (t == typeof(Byte))
                        {
                            // バイト配列はそのまま代入
                            bw.Write((byte[])objArray);
                        }
                        else if (t == typeof(String))
                        {
                            // 文字列配列をバイト配列に変換
                            foreach (String tmp in objArray)
                            {
                                bw.Write(bCAPByteConverter.Value2Byte((Int32)(2 * tmp.Length)));
                                bw.Write(bCAPByteConverter.Value2Byte(tmp));
                            }
                        }
                        else
                        {
                            // 各要素をバイト配列に変換
                            foreach (object tmp in objArray)
                            {
                                bw.Write(bCAPByteConverter.Value2Byte(tmp));
                            }
                        }
                    }
                }
                else
                {
                    // TypeをVARENUMに変換
                    VARENUM vt = FindVtFromType(t);

                    // 型: vt, 要素数: 1
                    bw.Write(bCAPByteConverter.Value2Byte((Int16)vt));
                    bw.Write(bCAPByteConverter.Value2Byte((Int32)1));

                    if (t == typeof(Byte))
                    {
                        // バイトはそのまま代入
                        bw.Write((byte)obj);
                    }
                    else if (t == typeof(String))
                    {
                        // 文字列をバイト配列に変換
                        bw.Write(bCAPByteConverter.Value2Byte((Int32)(2 * ((String)obj).Length)));
                        bw.Write(bCAPByteConverter.Value2Byte(obj));
                    }
                    else
                    {
                        // 要素をバイト配列に変換
                        bw.Write(bCAPByteConverter.Value2Byte(obj));
                    }
                }
            }
        }

        public bCAPPacket Deserialize(byte[] buf)
        {
            var packet = new bCAPPacket();

            try
            {
                using (var ms = new MemoryStream(buf))
                {
                    using (var br = new BinaryReader(ms))
                    {
                        // Message Length
                        br.BaseStream.Position = 1;
                        Int32 len = (Int32)bCAPByteConverter.Byte2Value(br.ReadBytes(4), typeof(Int32));

                        // パケットの確認
                        if ((buf[0] != BCAP_SOH) ||
                            (len > buf.Length) || (buf[len - 1] != BCAP_EOT))
                        {
                            throw new ArgumentException();
                        }

                        // Serial Number
                        packet.iSerial = (UInt16)bCAPByteConverter.Byte2Value(br.ReadBytes(2), typeof(UInt16));

                        // Reserve Number
                        packet.iReserv = (UInt16)bCAPByteConverter.Byte2Value(br.ReadBytes(2), typeof(UInt16));

                        // Function ID
                        packet.iFuncID = (Int32)bCAPByteConverter.Byte2Value(br.ReadBytes(4), typeof(Int32));

                        // Number of arguments
                        Int16 argc = (Int16)bCAPByteConverter.Byte2Value(br.ReadBytes(2), typeof(Int16));

                        // Arguments
                        packet.aryArgs.AddRange(Deserialize_ObjectArray(br, argc, true));
                    }
                }
            }
            catch (Exception ex)
            {
                throw new ORiN2Exception(HResult.E_INVALIDPACKET, ex);
            }

            return packet;
        }

        private ArrayList Deserialize_ObjectArray(BinaryReader br, int argc, bool first = false)
        {
            ArrayList args = new ArrayList(argc);

            for (int i = 0; i < argc; i++)
            {
                if (first)
                {
                    // 引数サイズをスキップ
                    br.BaseStream.Position += 4;
                }

                // 引数を変換して追加
                args.Add(Deserialize_Object(br));
            }

            return args;
        }

        private object Deserialize_Object(BinaryReader br)
        {
            object obj = null;

            // 引数の型
            Int16 vt = (Int16)bCAPByteConverter.Byte2Value(br.ReadBytes(2), typeof(Int16));
            
            // 要素数
            Int32 argc = (Int32)bCAPByteConverter.Byte2Value(br.ReadBytes(4), typeof(Int32));

            if ((vt & (Int16)VARENUM.VT_ARRAY) != 0)
            {
                vt ^= (Int16)VARENUM.VT_ARRAY;

                if (vt == (Int16)VARENUM.VT_VARIANT)
                {
                    // 型: (VT_VARIANT | VT_ARRAY), 要素数: argc
                    ArrayList objArray = Deserialize_ObjectArray(br, argc);
                    obj = objArray.ToArray();
                }
                else
                {
                    var pair = m_vt2type[(VARENUM)vt];

                    // VARENUMをTypeに変換
                    Type t = pair.Key;

                    // Typeのバイト数を取得
                    Int32 len = pair.Value;

                    if (t == typeof(Byte))
                    {
                        // バイト配列はそのまま代入
                        obj = br.ReadBytes(argc);
                    }
                    else if (t == typeof(String))
                    {
                        // バイト配列を文字列配列に変換
                        String[] strArray = new String[argc];
                        for (int i = 0; i < argc; i++)
                        {
                            len = (Int32)bCAPByteConverter.Byte2Value(br.ReadBytes(4), typeof(Int32));
                            strArray[i] = (String)bCAPByteConverter.Byte2Value(br.ReadBytes(len), t);
                        }
                        obj = strArray;
                    }
                    else
                    {
                        // バイト配列を各要素に変換
                        ArrayList objArray = new ArrayList(argc);
                        for (int i = 0; i < argc; i++)
                        {
                            objArray.Add(bCAPByteConverter.Byte2Value(br.ReadBytes(len), t));
                        }
                        obj = objArray.ToArray(t);
                    }
                }
            }
            else
            {
                var pair = m_vt2type[(VARENUM)vt];

                // VARENUMをTypeに変換
                Type t = pair.Key;

                // Typeのバイト数を取得
                Int32 len = pair.Value;

                if (t == null)
                {
                    obj = null;
                }
                else if (t == typeof(Byte))
                {
                    // バイトはそのまま代入
                    obj = br.ReadByte();
                }
                else if (t == typeof(String))
                {
                    // バイト配列を文字列に変換
                    len = (Int32)bCAPByteConverter.Byte2Value(br.ReadBytes(4), typeof(Int32));
                    obj = bCAPByteConverter.Byte2Value(br.ReadBytes(len), t);
                }
                else
                {
                    // バイト配列を要素に変換
                    obj = bCAPByteConverter.Byte2Value(br.ReadBytes(len), t);
                }
            }

            return obj;
        }
    }
}
