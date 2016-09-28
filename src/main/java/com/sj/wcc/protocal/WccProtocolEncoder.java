package com.sj.wcc.protocal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class WccProtocolEncoder implements ProtocolEncoder{

	Logger logger = Logger.getLogger(WccProtocolEncoder.class);
	
	@Override
	public void dispose(IoSession arg0) throws Exception {
		
	}

	@Override
	public void encode(IoSession arg0, Object arg1, ProtocolEncoderOutput out)
			throws Exception {
		logger.debug("encode Object:"+arg1);
		byte[] bytes = this.getByteForobj(arg1);
        IoBuffer buff = IoBuffer.allocate(bytes.length);
        buff.setAutoExpand(true);       
        buff.put(bytes);
        buff.flip();
        out.write(buff);
	}
	
	private byte[] getByteForobj(Object obj){
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
