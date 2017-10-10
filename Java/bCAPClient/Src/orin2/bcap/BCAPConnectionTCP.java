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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.InetSocketAddress;

import orin2.library.HResult;
import orin2.library.ORiN2Exception;

class BCAPConnectionTCP extends BCAPConnectionBase implements Runnable {
	private static final int PROC_INIT       = 1;
	private static final int PROC_RELEASE    = 2;
	private static final int PROC_SENDRECV   = 3;
	private static final int PROC_SETTIMEOUT = 4;
	
	private Socket              m_sock   = null;
	private DataInputStream     m_in     = null;
	private DataOutputStream    m_out    = null;
	private BCAPPacketConverter m_conv   = new BCAPPacketConverter();
	private Object[]            m_args   = null;
	private BCAPPacket          m_packet = null;
	private Throwable           m_except = null;
	
	public BCAPConnectionTCP(String host, int port, int timeout, int retry) throws Throwable {
		m_args = new Object[]{PROC_INIT, host, port, timeout, retry};
		
		Thread th = new Thread(this);		
		th.start();
		WaitThread(th);
				
		m_args = null;
		if(m_except != null) throw m_except;
		
		SetTimeout(timeout);
		SetRetry(retry);
	}
	
	@Override
	public void Release() {
		m_args = new Object[]{PROC_RELEASE};
		
		Thread th = new Thread(this);
		th.start();
		WaitThread(th);
		
		m_args = null;
	}
	
	@Override
	public synchronized BCAPPacket SendRecv(BCAPPacket packet) throws Throwable {
		m_args = new Object[]{PROC_SENDRECV, packet};
		
		Thread th = new Thread(this);
		th.start();
		WaitThread(th);
		
		m_args = null;
		if(m_except != null) throw m_except;
		return m_packet;
	}
	
	@Override
	public void SetTimeout(int timeout) throws Throwable {
		m_args = new Object[]{PROC_SETTIMEOUT, timeout};
		
		Thread th = new Thread(this);		
		th.start();
		WaitThread(th);
		
		m_args = null;
		if(m_except != null) throw m_except;
	}
	
	@Override
	public void SetRetry(int retry) throws Throwable {
		super.SetRetry(1);
	}
	
	@Override
	public void run() {
		m_except = null;
		try {
			switch((Integer)m_args[0]){
				case PROC_INIT:
					th_init((String)m_args[1], (Integer)m_args[2], (Integer)m_args[3]);
					break;
				case PROC_RELEASE:
					th_release();
					break;
				case PROC_SENDRECV:
					th_sendrecv((BCAPPacket)m_args[1]);
					break;
				case PROC_SETTIMEOUT:
					th_puttimeout((Integer)m_args[1]);
					break;
			}
		} catch(Throwable cause) {
			m_except = cause;
		}
	}
	
	private void th_init(String host, int port, int timeout) throws Throwable {
		m_sock = new Socket();
		m_sock.connect(new InetSocketAddress(host, port), timeout);
		m_sock.setTcpNoDelay(true);
		m_in = new DataInputStream(m_sock.getInputStream());
		m_out = new DataOutputStream(m_sock.getOutputStream());
	}

	private void th_release(){
		try { m_out.close(); }
		catch(Throwable ignore) { ; }
		finally { m_out = null; }
		
		try { m_in.close(); }
		catch(Throwable ignore) { ; }
		finally { m_in = null; }
		
		try { m_sock.close(); }
		catch(Throwable ignore) { ;	}
		finally { m_sock = null; }
	}
	
	private void th_sendrecv(BCAPPacket packet) throws Throwable {
		byte[] bRecv, bLen = new byte[4];
		m_packet = new BCAPPacket();
		
		// Send packet
		packet.Serial = m_serial;
		packet.Reserv = 0;
		m_out.write(m_conv.Serialize(packet));
		
		// Increments serial number
		m_serial = (short)((m_serial != -1) ? m_serial + 1 : 1);
		
		while(true) {
			int soh = m_in.read(); // SOH
			
			m_in.read(bLen); // Length
			int len = (Integer)BCAPByteConverter.Byte2Object(bLen, Integer.class);
			
			bRecv = new byte[len];
			m_in.readFully(bRecv, 5, len - 5);
			
			// Copy SOH and Length
			bRecv[0] = (byte)soh;
			System.arraycopy(bLen, 0, bRecv, 1, 4);
			
			m_packet = m_conv.Deserialize(bRecv);
			if((packet.Serial == m_packet.Serial)
					&& (m_packet.FuncID != HResult.S_EXECUTING)){
				break;
			}
		}
		
		if(HResult.FAILED(m_packet.FuncID)) {
			throw new ORiN2Exception(m_packet.FuncID);
		}
	}
	
	private void th_puttimeout(int timeout) throws Throwable {
		if(timeout < 0)
		{
			m_sock.setSoTimeout(0);
			super.SetTimeout(0);
		}
		else
		{
			m_sock.setSoTimeout(timeout);
			super.SetTimeout(timeout);
		}
	}
}
