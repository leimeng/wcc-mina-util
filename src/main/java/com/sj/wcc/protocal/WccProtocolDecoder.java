package com.sj.wcc.protocal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;


public class WccProtocolDecoder implements ProtocolDecoder{

	Logger logger = Logger.getLogger(WccProtocolDecoder.class);
	
	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
		logger.debug("decode IoBuffer:"+in);
		byte[] b = new byte[in.limit()];
		in.get(b);
		WccTransferObj response = (WccTransferObj)this.ByteToObject(b);
		out.write(response);
	}

	@Override
	public void dispose(IoSession arg0) throws Exception {
		
	}

	@Override
	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1)
			throws Exception {
		
	}
	
	private java.lang.Object ByteToObject(byte[] bytes) {
		java.lang.Object obj = null;
		ByteArrayInputStream bi = null;
		ObjectInputStream oi = null;
		try {
			bi = new ByteArrayInputStream(bytes);
			oi = new ObjectInputStream(bi);
			obj = oi.readObject();
		} catch (Exception e) {
			logger.error("translation" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(bi != null){
					bi.close();
				}
				
				if(oi != null){
					oi.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}
}
