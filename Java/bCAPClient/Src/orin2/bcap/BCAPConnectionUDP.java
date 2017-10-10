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

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import orin2.library.HResult;
import orin2.library.ORiN2Exception;

class BCAPConnectionUDP extends BCAPConnectionBase implements Runnable {
	private static final int PROC_SENDRECV = 1;
	
	private static final int PACKET_MAX = 504;
	private static final int RETRY_MIN  = 1;
    private static final int RETRY_MAX  = 7;
	
	private InetSocketAddress   m_addr    = null;
	private DatagramSocket      m_sock    = null;
	private BCAPPacketConverter m_conv    = new BCAPPacketConverter();
	private Object[]            m_args    = null;
	private BCAPPacket          m_packet  = null;
	private Throwable           m_except  = null;
	
	public BCAPConnectionUDP(String host, int port, int timeout, int retry) throws Throwable {
		m_addr = new InetSocketAddress(host, port);
		m_sock = new DatagramSocket();
		SetTimeout(timeout);
		SetRetry(retry);
	}
	
	@Override
	public void Release() {
		if(m_sock != null)
		{
			m_sock.close();
			m_sock = null;
		}
		
		m_addr = null;
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
	
	@Override
	public void SetRetry(int retry) throws Throwable {
		if(retry < RETRY_MIN)
		{
			super.SetRetry(RETRY_MIN);
		}
		else if(RETRY_MAX < retry)
		{
			super.SetRetry(RETRY_MAX);
		}
		else
		{
			super.SetRetry(retry);
		}
	}
	
	@Override
	public void run() {
		m_except = null;
		try {
			switch((Integer)m_args[0]){
				case PROC_SENDRECV:
					th_sendrecv((BCAPPacket)m_args[1]);
					break;
			}
		} catch(Throwable cause) {
			m_except = cause;
		}
	}
	
	private void th_sendrecv(BCAPPacket packet) throws Throwable {
		byte[] bSend, bRecv = new byte[PACKET_MAX];
		m_packet = new BCAPPacket();
		
		packet.Serial = m_serial;
		packet.Reserv = m_serial;
		
		for(int retry = 0; retry < m_retry; retry++) {
			try {
				packet.Serial = m_serial;
				bSend = m_conv.Serialize(packet);
				m_sock.send(new DatagramPacket(bSend, bSend.length, m_addr));
				
				// Increments serial number
				m_serial = (short)((m_serial != -1) ? m_serial + 1 : 1);
				
				while(true) {
					m_sock.receive(new DatagramPacket(bRecv, bRecv.length));
					
					m_packet = m_conv.Deserialize(bRecv);
					if((packet.Serial == m_packet.Serial)
							&& (m_packet.FuncID != HResult.S_EXECUTING)){
						break;
					}
				}
				
				break;
			} catch(Throwable cause) {
				if(retry >= m_retry - 1) throw cause;
			}
		}
		
		if(HResult.FAILED(m_packet.FuncID)) {
			throw new ORiN2Exception(m_packet.FuncID);
		}
	}
}
