package com.sj.wcc.util;

import java.util.ResourceBundle;

public class WccProperties {
	
	static ResourceBundle bundle = null;
	
	static{
		bundle = ResourceBundle.getBundle("server.application");
	}
	
	public static String getString(String key){
		return bundle.getString(key);
	}
	
	public static int getInt(String key){
		return Integer.parseInt(bundle.getString(key));
	}
	
	public static Object getObject(String key){
		return bundle.getObject(key);
	}
	
	public static void main(String[] args) {
	}
}
