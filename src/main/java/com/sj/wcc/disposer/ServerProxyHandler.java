package com.sj.wcc.disposer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.sj.wcc.protocal.WccRequest;
import com.sj.wcc.protocal.WccResponse;
import com.sj.wcc.util.WccSerialUtil;

public class ServerProxyHandler implements InvocationHandler{

	Logger logger = Logger.getLogger(ServerProxyHandler.class);
	
	Class<?> inFace ;
	WccIDisposer disposer;
	
	public ServerProxyHandler(Class<?> inFace, WccIDisposer disposer) {
		this.inFace = inFace;
		this.disposer = disposer;
	}

	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2)
			throws Throwable {
		WccRequest request = new WccRequest();
		request.setInFace(inFace.getCanonicalName());
		request.setMehtod(arg1.getName());
		request.setParamType(arg1.getParameterTypes());
		request.setParamValue(arg2);
		request.setRequesNo(WccSerialUtil.getSerialNo(inFace.getCanonicalName()));
		FutureRestlt future = disposer.sendRequset(request);
		ServerFutureCache.putFutureResult(request.getRequesNo(), future);
		Object obj = future.getResultValue();
		ServerFutureCache.removeFuture(request.getRequesNo());
		return obj;
	}
	
	private Object sendData(WccRequest request) throws Exception{
		Object returnValue = null;
		Socket socket=new Socket("127.0.0.1",8888);
		OutputStream outS = socket.getOutputStream();
        byte[] bytes = this.getByteForobj(request);
        outS.write(bytes);
		
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Object obj = ois.readObject();
        if(obj instanceof WccResponse){
        	WccResponse response = (WccResponse)obj;
        	returnValue = response.getReturnValue();
        }
		socket.close();
		return returnValue;
	}
	
	private Object getObjectForStream(InputStream in){
		byte[] bytes = this.getByteForStream(in);
		ByteArrayInputStream inS = new ByteArrayInputStream(bytes);
		ObjectInputStream objIn = null;
		try {
			objIn = new ObjectInputStream(inS);
			return objIn.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private byte[] getByteForStream(InputStream in) {
		try {
			byte[] bytes = new byte[in.available()+4];
			in.read(bytes);
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
