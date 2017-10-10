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

package orin2.bcap;

abstract class BCAPConnectionBase {
	protected short m_serial  = 1;
	protected int   m_timeout = 0;
	protected int   m_retry   = 0;
	
	public BCAPConnectionBase() {}
	
	public abstract void Release();
	public abstract BCAPPacket SendRecv(BCAPPacket packet) throws Throwable ;
	
	public int GetTimeout() {
		return m_timeout;
	}
	
	public void SetTimeout(int timeout) throws Throwable {
		m_timeout = timeout;
	}
	
	public int GetRetry() {
		return m_retry;
	}
	
	public void SetRetry(int retry) throws Throwable {
		m_retry = retry;
	}
	
	protected void WaitThread(Thread th)
	{
		while(th.isAlive()){
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){}
		}
	}
}
