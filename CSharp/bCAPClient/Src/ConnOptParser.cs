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
using System.Reflection;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace ORiN2.Library
{
    struct ConnOptEther
    {
        public string DstAddr;
        public int DstPort;
        public string SrcAddr;
        public int SrcPort;

        public ConnOptEther(string DstAddr, int DstPort, string SrcAddr, int SrcPort)
        {
            this.DstAddr = DstAddr;
            this.DstPort = DstPort;
            this.SrcAddr = SrcAddr;
            this.SrcPort = SrcPort;
        }
    }

    static class ConnOptParser
    {
        private const string REGEX_OPT_ETHER = @"^(?i)(tc|ud)p$";
        private const string REGEX_IPADDRESS = @"^(\d{0,3})\.(\d{0,3})\.(\d{0,3})\.(\d{0,3})$";
        private const int IPADDRESS_MAX_VALUE = 255;

        // tcp[:DstAddr[:DstPort[:SrcAddr[:SrdPort]]]]
        // udp[:DstAddr[:DstPort[:SrcAddr[:SrdPort]]]]
        public static ConnOptEther ParseEtherOption(string strOpt)
        {
            object optEth = new ConnOptEther("192.168.0.1", 5007, "0.0.0.0", 0);

            string[] strSplit = strOpt.Split(new string[] { ":" }, 5, StringSplitOptions.None);
            var fields = typeof(ConnOptEther).GetFields();

            FieldInfo fi = null;
            for (int i = 0; i < strSplit.Length; i++)
            {
                if (i != 0) fi = fields[i - 1];
                switch (i)
                {
                    case 0: // プロトコル
                        if (!Regex.IsMatch(strSplit[i], REGEX_OPT_ETHER))
                            throw new ArgumentException();
                        break;
                    case 1: // IPアドレス
                    case 3:
                        if (!IsIPAddress(ref strSplit[i]))
                            throw new ArgumentException();
                        fi.SetValue(optEth, strSplit[i]);
                        break;
                    case 2: // ポート番号
                    case 4:
                        int iTmp;
                        if (!int.TryParse(strSplit[i], out iTmp))
                            throw new ArgumentException();
                        fi.SetValue(optEth, iTmp);
                        break;
                }
            }

            return (ConnOptEther)optEth;
        }

        public static bool IsIPAddress(ref string strOpt, bool bCoerce = false)
        {
            bool bRet = true;

            var reg = new Regex(REGEX_IPADDRESS);
            var match = reg.Match(strOpt);

            if ((!match.Success) || (match.Groups.Count < 5))
                return false;

            int iTmp;
            string strTmp = "";
            for (int i = 1; i < match.Groups.Count; i++)
            {
                if (bCoerce)
                {
                    if (match.Groups[i].Value != "")
                    {
                        // 数値に変換
                        int.TryParse(match.Groups[i].Value, out iTmp);
                    }else{
                        // 空文字は"0"に置換
                        iTmp = 0;
                    }

                    // 255より大きい場合は255に強制
                    if (iTmp > IPADDRESS_MAX_VALUE) iTmp = IPADDRESS_MAX_VALUE;

                    // 文字列を再連結（0始まりの文字対策）
                    strTmp += iTmp.ToString();
                    if (i != (match.Groups.Count - 1)) strTmp += ".";
                }
                else
                {
                    // 数値に変換できるか
                    bRet &= int.TryParse(match.Groups[i].Value, out iTmp);

                    // 255以下か
                    bRet &= (iTmp <= IPADDRESS_MAX_VALUE);

                    // 元の文字列と一致するか
                    bRet &= match.Groups[i].Value.Equals(iTmp.ToString());
                }
            }

            if (bCoerce)
            {
                strOpt = strTmp;
            }

            return bRet;
        }
    }
}
