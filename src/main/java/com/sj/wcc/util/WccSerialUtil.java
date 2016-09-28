package com.sj.wcc.util;

public class WccSerialUtil {
	public static String getSerialNo(String imp){
		synchronized (WccSerialUtil.class) {
			long time = System.currentTimeMillis();
			return imp + "_" + time;
		}
	}
}
