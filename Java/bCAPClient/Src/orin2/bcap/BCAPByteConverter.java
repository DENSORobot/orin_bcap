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

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;
import java.util.Hashtable;
import java.util.TimeZone;

class BCAPByteConverter {
	private static final ByteOrder BYTE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
	private static final String	STRING_ENDIAN = "UTF-16LE";
	
	private static Hashtable<Class<?>, BaseConverter> m_htClsCvt;
	
	static {
		BCAPByteConverter pthis = new BCAPByteConverter();
		m_htClsCvt = new Hashtable<Class<?>, BaseConverter>();
		m_htClsCvt.put(short.class  , pthis.new ShortConverter() ); // VT_I2, VT_UI2
		m_htClsCvt.put(Short.class  , pthis.new ShortConverter() ); // VT_I2, VT_UI2
		m_htClsCvt.put(int.class    , pthis.new IntConverter()   ); // VT_I4, VT_UI4
		m_htClsCvt.put(Integer.class, pthis.new IntConverter()   ); // VT_I4, VT_UI4
		m_htClsCvt.put(long.class   , pthis.new LongConverter()    ); // VT_I8, VT_UI8
		m_htClsCvt.put(Long.class   , pthis.new LongConverter()    ); // VT_I8, VT_UI8
		m_htClsCvt.put(float.class  , pthis.new FloatConverter() ); // VT_R4
		m_htClsCvt.put(Float.class  , pthis.new FloatConverter() ); // VT_R4
		m_htClsCvt.put(double.class , pthis.new DoubleConverter()); // VT_R8
		m_htClsCvt.put(Double.class , pthis.new DoubleConverter()); // VT_R8
		m_htClsCvt.put(BigDecimal.class   , pthis.new CyConverter()    ); // VT_CY
		m_htClsCvt.put(Date.class   , pthis.new DateConverter()  ); // VT_DATE
		m_htClsCvt.put(String.class , pthis.new StringConverter()); // VT_BSTR
		m_htClsCvt.put(boolean.class, pthis.new BoolConverter()  ); // VT_BOOL
		m_htClsCvt.put(Boolean.class, pthis.new BoolConverter()  ); // VT_BOOL
	}
	
	public static Object Byte2Object(byte[] byt, Class<?> c) throws Throwable {
		BaseConverter cvt = m_htClsCvt.get(c);
		return cvt.Byte2Object(byt);
	}
	
	public static byte[] Object2Byte(Object obj) throws Throwable {
		BaseConverter cvt = m_htClsCvt.get(obj.getClass());
		return cvt.Object2Byte(obj);
	}
	
	private abstract class BaseConverter {
		public abstract Object Byte2Object(byte[] byt) throws Throwable;
		public abstract byte[] Object2Byte(Object obj) throws Throwable;
	}
	
	private class ShortConverter extends BaseConverter {
		@Override
		public Object Byte2Object(byte[] byt) throws Throwable {
			return (Object)ByteBuffer.wrap(byt).order(BYTE_ENDIAN).getShort();
		}
		
		@Override
		public byte[] Object2Byte(Object obj) throws Throwable {
			byte[] byt = new byte[2];
			ByteBuffer.wrap(byt).order(BYTE_ENDIAN).putShort((Short)obj);
			return byt;
		}
	}
	
	private class IntConverter extends BaseConverter {
		@Override
		public Object Byte2Object(byte[] byt) throws Throwable {
			return (Object)ByteBuffer.wrap(byt).order(BYTE_ENDIAN).getInt();
		}
		
		@Override
		public byte[] Object2Byte(Object obj) throws Throwable {
			byte[] byt = new byte[4];
			ByteBuffer.wrap(byt).order(BYTE_ENDIAN).putInt((Integer)obj);
			return byt;
		}
	}
	
	private class LongConverter extends BaseConverter {
		@Override
		public Object Byte2Object(byte[] byt) throws Throwable {
			return (Object)ByteBuffer.wrap(byt).order(BYTE_ENDIAN).getLong();
		}
		
		@Override
		public byte[] Object2Byte(Object obj) throws Throwable {
			byte[] byt = new byte[8];
			ByteBuffer.wrap(byt).order(BYTE_ENDIAN).putLong((Long)obj);
			return byt;
		}
	}	
	
	private class FloatConverter extends BaseConverter {
		@Override
		public Object Byte2Object(byte[] byt) throws Throwable {
			return (Object)ByteBuffer.wrap(byt).order(BYTE_ENDIAN).getFloat();
		}
		
		@Override
		public byte[] Object2Byte(Object obj) throws Throwable {
			byte[] byt = new byte[4];
			ByteBuffer.wrap(byt).order(BYTE_ENDIAN).putFloat((Float)obj);
			return byt;
		}
	}
	
	private class DoubleConverter extends BaseConverter {
		@Override
		public Object Byte2Object(byte[] byt) throws Throwable {
			return (Object)ByteBuffer.wrap(byt).order(BYTE_ENDIAN).getDouble();
		}
		
		@Override
		public byte[] Object2Byte(Object obj) throws Throwable {
			byte[] byt = new byte[8];
			ByteBuffer.wrap(byt).order(BYTE_ENDIAN).putDouble((Double)obj);
			return byt;
		}
	}
	
	private class CyConverter extends BaseConverter {
		@Override
		public Object Byte2Object(byte[] byt) throws Throwable {
			return (Object)BigDecimal.valueOf(ByteBuffer.wrap(byt).order(BYTE_ENDIAN).getLong());
		}
		
		@Override
		public byte[] Object2Byte(Object obj) throws Throwable {
			byte[] byt = new byte[8];
			ByteBuffer.wrap(byt).order(BYTE_ENDIAN).putLong(((BigDecimal)obj).longValue());
			return byt;
		}
	}
	
	private class DateConverter extends BaseConverter {
		private static final double   TIME_DIFFERENCE = 25569.0;
		private static final double   MSEC_ONEDAY     = 24 * 60 * 60 * 1000;

		@Override
		public Object Byte2Object(byte[] byt) throws Throwable {
			double dVal = ByteBuffer.wrap(byt).order(BYTE_ENDIAN).getDouble();
			return (Object)new Date((long)((dVal-TIME_DIFFERENCE)*MSEC_ONEDAY)-TimeZone.getDefault().getRawOffset());
		}
		
		@Override
		public byte[] Object2Byte(Object obj) throws Throwable {
			byte[] byt = new byte[8];
			double dVal = (double)(((Date)obj).getTime()+TimeZone.getDefault().getRawOffset())/MSEC_ONEDAY+TIME_DIFFERENCE;
			ByteBuffer.wrap(byt).order(BYTE_ENDIAN).putDouble(dVal);
			return byt;
		}
	}
	
	private class StringConverter extends BaseConverter {
		@Override
		public Object Byte2Object(byte[] byt) throws Throwable {
			return (Object)new String(byt, STRING_ENDIAN);
		}
		
		@Override
		public byte[] Object2Byte(Object obj) throws Throwable {
			return ((String)obj).getBytes(STRING_ENDIAN);
		}
	}
	
	private class BoolConverter extends BaseConverter {
		@Override
		public Object Byte2Object(byte[] byt) throws Throwable {
			short sVal = ByteBuffer.wrap(byt).order(BYTE_ENDIAN).getShort();
			return (Object)(sVal != 0 ? true : false);
		}
		
		@Override
		public byte[] Object2Byte(Object obj) throws Throwable {
			byte[] byt = new byte[2];
			short sVal = (short)(((Boolean)obj) ? -1 : 0);			
			ByteBuffer.wrap(byt).order(BYTE_ENDIAN).putShort(sVal);
			return byt;
		}
	}
}
