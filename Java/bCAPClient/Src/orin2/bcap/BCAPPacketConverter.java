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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map.Entry;

class BCAPPacketConverter {
	private static final int VT_EMPTY   = 0;
	private static final int VT_NULL    = 1;
	private static final int VT_ERROR   = 10;
	private static final int VT_UI1     = 17;
	private static final int VT_I2      = 2;
	private static final int VT_UI2     = 18;
	private static final int VT_I4      = 3;
	private static final int VT_UI4     = 19;
	private static final int VT_I8      = 20;
	private static final int VT_UI8     = 21;
	private static final int VT_R4      = 4;
	private static final int VT_R8      = 5;
	private static final int VT_CY	    = 6;
	private static final int VT_DATE    = 7;
	private static final int VT_BOOL    = 11;
	private static final int VT_BSTR    = 8;
	private static final int VT_VARIANT = 12;
	private static final int VT_ARRAY   = 0x2000;
	
	private static final int BCAP_SOH = 0x1;
	private static final int BCAP_EOT = 0x4;
	private static final int MAX_OPT_BYTES = 8;
	
	private static final int CRC_CCITT16 = 0x1021;
	
	private static Hashtable<Integer, Class<?>>  m_vt2cls;
	private static Hashtable<Integer, int[]>     m_vt2opt;
	private static Hashtable<Class<?>, Class<?>> m_autobox;
	
	static {
		m_vt2cls = new Hashtable<Integer, Class<?>>();
		
		m_vt2cls.put(VT_UI1    , Byte.class      );
		m_vt2cls.put(VT_I2     , Short.class     );
		m_vt2cls.put(VT_UI2    , Short.class     );
		m_vt2cls.put(VT_I4     , Integer.class   );
		m_vt2cls.put(VT_UI4    , Integer.class   );
		m_vt2cls.put(VT_I8     , Long.class      );
		m_vt2cls.put(VT_UI8    , Long.class      );		
		m_vt2cls.put(VT_ERROR  , Integer.class   );
		m_vt2cls.put(VT_R4     , Float.class     );
		m_vt2cls.put(VT_R8     , Double.class    );
		m_vt2cls.put(VT_CY     , BigDecimal.class);
		m_vt2cls.put(VT_DATE   , Date.class      );
		m_vt2cls.put(VT_BOOL   , Boolean.class   );
		m_vt2cls.put(VT_BSTR   , String.class    );
		
		// Array
		m_vt2cls.put(VT_ARRAY | VT_UI1    , byte[].class      );
		m_vt2cls.put(VT_ARRAY | VT_I2     , short[].class     );
		m_vt2cls.put(VT_ARRAY | VT_UI2    , short[].class     );
		m_vt2cls.put(VT_ARRAY | VT_I4     , int[].class       );
		m_vt2cls.put(VT_ARRAY | VT_UI4    , int[].class       );
		m_vt2cls.put(VT_ARRAY | VT_I8     , long[].class      );
		m_vt2cls.put(VT_ARRAY | VT_UI8    , long[].class      );
		m_vt2cls.put(VT_ARRAY | VT_R4     , float[].class     );
		m_vt2cls.put(VT_ARRAY | VT_R8     , double[].class    );
		m_vt2cls.put(VT_ARRAY | VT_CY     , BigDecimal[].class);
		m_vt2cls.put(VT_ARRAY | VT_DATE   , Date[].class      );
		m_vt2cls.put(VT_ARRAY | VT_BOOL   , boolean[].class   );
		m_vt2cls.put(VT_ARRAY | VT_BSTR   , String[].class    );
		m_vt2cls.put(VT_ARRAY | VT_VARIANT, Object[].class    );
		
		// int[2] -> [0]: the number of bytes, [1]: primary
		m_vt2opt = new Hashtable<Integer, int[]>();
		m_vt2opt.put(VT_UI1    , new int[]{1,1});
		m_vt2opt.put(VT_I2     , new int[]{2,1});
		m_vt2opt.put(VT_UI2    , new int[]{2,0});
		m_vt2opt.put(VT_I4     , new int[]{4,1});
		m_vt2opt.put(VT_UI4    , new int[]{4,0});
		m_vt2opt.put(VT_I8     , new int[]{8,1});
		m_vt2opt.put(VT_UI8    , new int[]{8,0});
		m_vt2opt.put(VT_ERROR  , new int[]{4,0});
		m_vt2opt.put(VT_R4     , new int[]{4,1});
		m_vt2opt.put(VT_R8     , new int[]{8,1});
		m_vt2opt.put(VT_CY     , new int[]{8,1});
		m_vt2opt.put(VT_DATE   , new int[]{8,1});
		m_vt2opt.put(VT_BOOL   , new int[]{2,1});
		m_vt2opt.put(VT_BSTR   , new int[]{4,1});
		m_vt2opt.put(VT_VARIANT, new int[]{0,1});
		
		m_autobox = new Hashtable<Class<?>, Class<?>>();
		m_autobox.put(Byte[].class   , byte[].class   );		
		m_autobox.put(Short[].class  , short[].class  );
		m_autobox.put(Integer[].class, int[].class    );
		m_autobox.put(Float[].class  , float[].class  );
		m_autobox.put(Double[].class , double[].class );
		m_autobox.put(Long[].class   , long[].class   );
		m_autobox.put(Boolean[].class, boolean[].class);
	}
	
	private static int FindVtFromClass(Class<?> cls) throws Throwable {
		int ret = -1, cnt = 0;
		// cnt=0: original class, =1: boxing class, >1: break
		while(ret == -1) {
			// if did not find from original class, then find from boxing class
			if(cnt == 1) {
				cls = m_autobox.get(cls);
			} else if(cnt > 1) {
				break;
			}
			
			for(Entry<Integer, Class<?>> e : m_vt2cls.entrySet()) {
				if(cls.equals(e.getValue())) {
					int tmp = e.getKey();
					int[] opt = m_vt2opt.get(tmp & ~VT_ARRAY);
					// if the found vt is primary, then return that
					if(opt[1] == 1) {
						ret = tmp;
						break;
					}
				}
			}
			
			cnt++;
		}
		
		return ret;
	}
	
	private boolean m_hasCRC;
	
	public BCAPPacketConverter() {
		this(false);
	}
	
	public BCAPPacketConverter(boolean hasCRC) {
		m_hasCRC = hasCRC;
	}
	
	public byte[] Serialize(BCAPPacket packet) throws IllegalArgumentException {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		byte[] b = null;
		
		try {
			bs.write(BCAP_SOH);
			bs.write(new byte[] {0, 0, 0, 0});
			bs.write(BCAPByteConverter.Object2Byte(packet.Serial));
			bs.write(BCAPByteConverter.Object2Byte(packet.Reserv));
			bs.write(BCAPByteConverter.Object2Byte(packet.FuncID));
			bs.write(BCAPByteConverter.Object2Byte((short)packet.Args.size()));
			Serialize_ObjectArray(bs, packet.Args, true);
			if(m_hasCRC) {
				bs.write(new byte[] {0, 0});
			}
			bs.write(BCAP_EOT);
			
			b = bs.toByteArray();
			ByteBuffer.wrap(b, 1, 4).put(BCAPByteConverter.Object2Byte(b.length));
			if(m_hasCRC) {
				ByteBuffer.wrap(b, b.length - 3, 2).put(
						BCAPByteConverter.Object2Byte((short)CalcCRC16(b, 1, b.length - 4)));
			}
		} catch(Throwable cause) {
			throw new IllegalArgumentException(cause);
		}
		
		return b;
	}
	
	private void Serialize_ObjectArray(ByteArrayOutputStream bs, ArrayList<Object> args, boolean first) throws Throwable {
		for(Object obj : args) {
			ByteArrayOutputStream _bs = new ByteArrayOutputStream();
						
			if (first) {
				_bs.write(new byte[] {0, 0, 0, 0});
			}
			
			Serialize_Object(_bs, obj);

			byte[] b = _bs.toByteArray();
			if (first) {
				ByteBuffer.wrap(b, 0, 4).put(BCAPByteConverter.Object2Byte(b.length - 4));
			}
			
			bs.write(b);
		}
	}
	
	private void Serialize_Object(ByteArrayOutputStream bs, Object arg) throws Throwable {
		if(arg == null) {
			bs.write(BCAPByteConverter.Object2Byte((short)VT_EMPTY));
			bs.write(BCAPByteConverter.Object2Byte(1));
			return;
		}
		
		Class<?> cls = arg.getClass();
		int vt = FindVtFromClass(cls);
		bs.write(BCAPByteConverter.Object2Byte((short)vt));
		
		if(cls.isArray()) {
			int argc = Array.getLength(arg);
			bs.write(BCAPByteConverter.Object2Byte(argc));
			
			switch(vt ^ VT_ARRAY) {
				case VT_UI1:
					if(cls.equals(byte[].class)) {
						bs.write((byte[])arg);
					} else {
						for(int i = 0; i < argc; i++) {
							bs.write((Byte)Array.get(arg, i));
						}
					}
					break;
				case VT_BSTR:
					for(int i = 0; i < argc; i++) {
						String str = (String)Array.get(arg, i);
						bs.write(BCAPByteConverter.Object2Byte(2 * str.length()));
						if(!str.equals("")) {
							bs.write(BCAPByteConverter.Object2Byte(str));
						}
					}
					break;
				case VT_VARIANT:
					ArrayList<Object> ary = new ArrayList<Object>(argc);
					for(int i = 0; i < argc; i++) {
						ary.add(Array.get(arg, i));
					}
					Serialize_ObjectArray(bs, ary, false);
					break;
				default:
					for(int i = 0; i < argc; i++) {
						bs.write(BCAPByteConverter.Object2Byte(Array.get(arg, i)));
					}
					break;
			}
		} else {
			bs.write(BCAPByteConverter.Object2Byte(1));
			
			switch(vt) {
				case VT_UI1:
					bs.write((Byte)arg);
					break;
				case VT_BSTR:
					String str = (String)arg;
					bs.write(BCAPByteConverter.Object2Byte(2 * str.length()));
					if(!str.equals("")) {
						bs.write(BCAPByteConverter.Object2Byte(str));
					}
					break;
				default:
					bs.write(BCAPByteConverter.Object2Byte(arg));
					break;
			}
		}
	}
	
	public BCAPPacket Deserialize(byte[] packet) throws IllegalArgumentException {
		BCAPPacket ret = new BCAPPacket();

		try {
			ByteArrayInputStream bs = new ByteArrayInputStream(packet);
			byte[] b = new byte[MAX_OPT_BYTES];
		
			int soh = bs.read(); // SOH
			
			bs.read(b, 0, 4); // Length
			int len = (Integer)BCAPByteConverter.Byte2Object(b, Integer.class);
			if(soh != BCAP_SOH || len > packet.length || packet[len - 1] != BCAP_EOT) {
				throw new IllegalArgumentException("The header or terminator is wrong.");
			}
			
			bs.read(b, 0, 2); // Serial
			ret.Serial = (Short)BCAPByteConverter.Byte2Object(b, Short.class);
			
			bs.read(b, 0, 2); // Reservation
			ret.Reserv = (Short)BCAPByteConverter.Byte2Object(b, Short.class);
			
			bs.read(b, 0, 4); // Function ID
			ret.FuncID = (Integer)BCAPByteConverter.Byte2Object(b, Integer.class);
			
			bs.read(b, 0, 2); // Number of arguments
			int argc = (Short)BCAPByteConverter.Byte2Object(b, Short.class);
			
			ret.Args = Deserialize_ObjectArray(bs, argc, true);
			
			if(m_hasCRC) {
				short crc_calc, crc_recv;
				crc_calc = (short)CalcCRC16(packet, 1, len - 4);
				bs.read(b, 0, 2);
				crc_recv = (Short)BCAPByteConverter.Byte2Object(b, Short.class);
				if(crc_calc != crc_recv) {
					throw new IllegalArgumentException("CRC is wrong.");
				}
			}
			
			bs.read(); // EOH
		} catch(Throwable cause) {
			throw new IllegalArgumentException(cause);
		}
		
		return ret;
	}
	
	private ArrayList<Object> Deserialize_ObjectArray(ByteArrayInputStream bs, int argc, boolean first) throws Throwable {
		ArrayList<Object> args = new ArrayList<Object>(argc);
		
		for(int i = 0; i < argc; i++) {
			if(first) {
				bs.skip(4);
			}
			
			args.add(Deserialize_Object(bs));
		}
		
		return args;
	}
	
	private Object Deserialize_Object(ByteArrayInputStream bs) throws Throwable {
		Object obj = null;
		byte[] bTmp, b = new byte[MAX_OPT_BYTES];
		
		bs.read(b, 0, 2);
		int vt = (Short)BCAPByteConverter.Byte2Object(b, Short.class);
		
		bs.read(b, 0, 4);
		int argc = (Integer)BCAPByteConverter.Byte2Object(b, Integer.class);
		
		if((vt & VT_ARRAY) != 0) {
			Class<?> cls  = m_vt2cls.get(vt).getComponentType();
			int[] opt = m_vt2opt.get(vt ^ VT_ARRAY);

			switch(vt ^ VT_ARRAY) {
				case VT_UI1:
					bTmp = new byte[argc];
					bs.read(bTmp, 0, argc);
					obj = bTmp;
					break;
				case VT_BSTR:
					obj = Array.newInstance(String.class, argc);
					for(int i = 0; i < argc; i++) {
						bs.read(b, 0, opt[0]);
						int len = (Integer)BCAPByteConverter.Byte2Object(b, Integer.class);
						if(len > 0) {
							bTmp = new byte[len];
							bs.read(bTmp, 0, len);
							Array.set(obj, i, BCAPByteConverter.Byte2Object(bTmp, String.class));
						} else Array.set(obj, i, "");
					}
					break;
				case VT_VARIANT:
					ArrayList<Object> ary = Deserialize_ObjectArray(bs, argc, false);
					obj = ary.toArray();
					break;
				default:
					obj = Array.newInstance(cls, argc);
					for(int i = 0; i < argc; i++) {
						bs.read(b, 0, opt[0]);
						Array.set(obj, i, BCAPByteConverter.Byte2Object(b, cls));
					}
					break;
			}
		} else {
			Class<?> cls = m_vt2cls.get(vt);
			int[] opt = m_vt2opt.get(vt);
			
			switch(vt) {
				case VT_EMPTY:
				case VT_NULL:
					break;
				case VT_UI1:
					obj = new Byte((byte)bs.read());
					break;
				case VT_BSTR:
					bs.read(b, 0, opt[0]);
					int len = (Integer)BCAPByteConverter.Byte2Object(b, Integer.class);
					if(len > 0) {
						bTmp = new byte[len];
						bs.read(bTmp, 0, len);
						obj = BCAPByteConverter.Byte2Object(bTmp, String.class);
					} else obj = "";
					break;
				default:
					bs.read(b, 0, opt[0]);
					obj = BCAPByteConverter.Byte2Object(b, cls);
					break;
			}
		}
		
		return obj;
	}
	
	private int CalcCRC16(byte[] bin, int offset, int length){
		int i, j, crc = 0xFFFF;
		
		for(i = offset; i < offset + length; i++){
			crc ^= (bin[i] << 8);
			for(j = 0; j < 8; j++){
				if((crc & 0x8000) != 0){
					crc <<= 1;
					crc ^= CRC_CCITT16;
				}else{
					crc <<= 1;
				}
			}
		}
		
		return crc;
	}	
}
