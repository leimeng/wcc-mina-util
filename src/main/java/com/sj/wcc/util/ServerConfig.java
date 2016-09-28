package com.sj.wcc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sj.wcc.WccException;

public class ServerConfig {
	static Logger logger = Logger.getLogger(ServerConfig.class);
	
	public static Map<String, ServerDef> scache = new HashMap<String, ServerDef>();
	public static Map<String, Object> service_instance = new HashMap<String, Object>();
	
	private static ServerConfig config = new ServerConfig();
	
	public static ServerConfig getInstance(){
		return config;
	}
	
	public ServerDef getServerDef(String imp){
		if(scache.containsKey(imp)){
			return scache.get(imp);
		}else{
			throw new WccException("interface error");
		}
	}
	
	public static void init(){
		
	}
	
	public Object getServerInpInstance(String imp){
		if(service_instance.containsKey(imp)){
			return service_instance.get(imp);
		}
		synchronized(this){
			if(scache.containsKey(imp)){
				try {
					ServerDef def = scache.get(imp);
					Class<?> clazz = Class.forName(def.getInstance());
					Object obj = clazz.newInstance();
					service_instance.put(imp, obj);
					return obj; 
				} catch (Exception e) { 
					logger.error("create service instance error", e);
				} 
			}else{
				logger.error("error interface");
				throw new RuntimeException("error interface");
			}
		}
		return null;
	}
	
	
	public static class ServerDef {
		private String imp;
		private String instance;
		private String enabled;
		private List<MethodEx> methods = new ArrayList<MethodEx>();
		private Object locker = new Object();
		
		public ServerDef() {
		}
		
		public ServerDef(String imp, String enabled,
				List<MethodEx> methods) {
			this.imp = imp;
			this.enabled = enabled;
			this.methods = methods;
			this.locker = false;
		}

		public ServerDef(String imp, String enabled,
				List<MethodEx> methods, Object locker) {
			this.imp = imp;
			this.enabled = enabled;
			this.methods = methods;
			this.locker = locker;
		}
		public String getImp() {
			return imp;
		}
		public void setImp(String imp) {
			this.imp = imp;
		}
		public String getInstance() {
			return instance;
		}

		public void setInstance(String instance) {
			this.instance = instance;
		}

		public String getEnadled() {
			return enabled;
		}
		public void setEnadled(String enabled) {
			this.enabled = enabled;
		}
		
		public List<MethodEx> getMethods() {
			return methods;
		}

		public Object getLocker() {
			return locker;
		}
		public void setLocker(Object locker) {
			this.locker = locker;
		}
		
		public void addMethod(MethodEx mex){
			this.methods.add(mex);
		}
		
		public MethodEx getMethodEx(String name , Class<?>... type){
			for(int i = 0 ; i < this.methods.size() ; i++){
				MethodEx m = this.methods.get(i);
				Class<?>[] pType = m.method.getParameterTypes();
				if(checkMethodType(type, pType)){
					return m;
				}
			}
			throw new WccException("no method");
		}
		
		private boolean checkMethodType(Class<?>[] type1,Class<?>[] type2){
			if(type1.length != type2.length){
				return false;
			}
			
			for(int i = 0 ; i < type1.length ; i++){
				if(!type1[i].getCanonicalName().equals(type2[i].getCanonicalName())){
					return false;
				}
			}
			return true;
		}
	}
}


