package com.sj.wcc.task;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.sj.wcc.WccException;
import com.sj.wcc.protocal.WccRequest;
import com.sj.wcc.protocal.WccResponse;
import com.sj.wcc.util.MethodEx;
import com.sj.wcc.util.ServerConfig;
import com.sj.wcc.util.ServerConfig.ServerDef;

public class DealReqTask extends TaskImp{
	private Logger logger = Logger.getLogger(DealReqTask.class);
	private WccRequest request;
	private IoSession session;
	private boolean withTransaction = false;
	
	public DealReqTask(IoSession session, WccRequest request) {
		this(request,session,false,false);
	}
	
	public DealReqTask(IoSession session, WccRequest request, boolean joinMonFlag) {
		this(request,session,false,joinMonFlag);
	}
	
	public DealReqTask(WccRequest request,
			IoSession session, boolean withTransaction,boolean joinMonFlag) {
		this.request = request;
		this.session = session;
		this.withTransaction = withTransaction;
		this.joinMonFlag = joinMonFlag;
	}
	
	public DealReqTask(WccRequest request,
			IoSession session, boolean withTransaction) {
		this(request,session,false,false);
	}

	public DealReqTask(WccRequest request,boolean withTransaction) {
		this(request,null,withTransaction);
	}

	@Override
	public void run() {
		this.routine();
	}

	@Override
	protected void routine() {
		logger.info("request interface:" + request.getInFace());
		ServerConfig config = ServerConfig.getInstance();
		ServerDef def = config.getServerDef(request.getInFace());
		Object obj = config.getServerInpInstance(request.getInFace());
		MethodEx methodEx = def.getMethodEx(request.getMehtod(), request.getParamType());
		if(this.withTransaction){
			
		}else{
			try {
				Method method = methodEx.method;
				Object returnValue = method.invoke(obj, request.getParamValue());
				this.sendData(request, returnValue, method.getReturnType());
			} catch (Exception e) {
				logger.error("", e);
				throw new WccException("invoke error");
			} 
		}
	}
	
	private void sendData(WccRequest request, Object returnValue,Class<?> returnType){
		if("void".equalsIgnoreCase(returnType.getName())){
			return;
		}
		WccResponse respnse = new WccResponse();
		respnse.setRequesNo(request.getRequesNo());
		respnse.setReturnValue(returnValue);
		session.write(respnse);
	}
}
