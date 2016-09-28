package com.sj.wcc.disposer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.apache.log4j.Logger;

public class RequestServerProxy {
	
	static Logger logger = Logger.getLogger(RequestServerProxy.class);
	
	private WccIDisposer disposer;
	
	public RequestServerProxy() {
	}

	public RequestServerProxy(WccIDisposer disposer) {
		this.disposer = disposer;
	}

	@SuppressWarnings("unchecked")
	public <T> T getServerInterface(Class<?> clazz){
		InvocationHandler handler = new ServerProxyHandler(clazz,disposer);
		Class<?>[] clazzs = new Class<?>[]{clazz};
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazzs, handler);
	}

	public  void setDisposer(WccIDisposer disposer) {
		logger.debug("setDisposer " + disposer);
		this.disposer = disposer;
	} 
}
