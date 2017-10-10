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

import java.util.Hashtable;

public class ORiN2Exception extends Exception {
	private static final long serialVersionUID = 1L;
	
	// Static private variable
	private static Hashtable<Integer, String> m_hr2msg;
	
	// Static functions
	static {
		m_hr2msg = new Hashtable<Integer, String>();
	}
	
	private static String HResult2Message(int hr) {
		String strRet = String.format("(0x%08x)", hr);
		
		if(m_hr2msg.contains(hr)) {
			strRet += m_hr2msg.get(hr);
		}
		
		return strRet;
	}
	
	// private variable
	private int m_hr;
	
	public ORiN2Exception(int hr) {
		super(HResult2Message(hr));
		m_hr = hr;
	}
	
	public int HResult() {
		return m_hr;
	}
}
