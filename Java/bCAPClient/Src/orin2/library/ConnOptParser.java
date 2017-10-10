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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnOptParser {
    private static final String REGEX_OPT_ETHER = "^(?i)(tc|ud)p$";
    private static final String REGEX_IPADDRESS = "^(\\d{0,3})\\.(\\d{0,3})\\.(\\d{0,3})\\.(\\d{0,3})$";
    private static final int IPADDRESS_MAX_VALUE = 255;

    // tcp[:DstAddr[:DstPort[:SrcAddr[:SrdPort]]]]
    // udp[:DstAddr[:DstPort[:SrcAddr[:SrdPort]]]]
    public static Object[] ParseEtherOption(String strOpt) throws IllegalArgumentException
    {
        Object[] ret = new Object[] { "192.168.0.1", 5007, "0.0.0.0", 0 };

        String[] strSplit = strOpt.split(":", 5);
        for (int i = 0; i < strSplit.length; i++)
        {
            switch (i)
            {
                case 0: // Protocol
                	if(!Pattern.matches(REGEX_OPT_ETHER, strSplit[i])) {
                		throw new IllegalArgumentException();
                	}
                    break;
                case 1: // IP Address
                case 3:
                	if(!IsIPAddress(strSplit[i])) {
                		throw new IllegalArgumentException();
                	}
                	ret[i - 1] = strSplit[i];
                    break;
                case 2: // Port number
                case 4:
                	Integer iTmp = (Integer)ret[i - 1];
                	try {
                		iTmp = new Integer(strSplit[i]);
                	} catch (NumberFormatException cause) {
                		throw new IllegalArgumentException(cause);
                	}
                	ret[i - 1] = iTmp;
                    break;
            }
        }

        return ret;
    }
    
    public static boolean IsIPAddress(String strOpt) {
    	boolean bRet = true;
    	
    	Pattern p = Pattern.compile(REGEX_IPADDRESS);
    	Matcher m = p.matcher(strOpt);

    	if(!m.matches() || m.groupCount() < 4)
    		return false;

    	for(int i = 1; i < m.groupCount(); i++) {
    		Integer iTmp = -1;
    		String strTmp = m.group(i);
    		
    		try {
    			iTmp = new Integer(strTmp);
    		} catch (NumberFormatException cause) {
    			bRet &= false;
    		}
    		
    		bRet &= (iTmp <= IPADDRESS_MAX_VALUE);
    		bRet &= strTmp.equals(iTmp.toString());
    	}
    	
    	return bRet;
    }
}
