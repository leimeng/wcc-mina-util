package com.sj.wcc.disposer;

import java.util.HashMap;
import java.util.Map;

public class ServerFutureCache {
	static Map<String, FutureRestlt> caches = new HashMap<String, FutureRestlt>();
	
	public static void putFutureResult(String requesNo,FutureRestlt future){
		caches.put(requesNo, future);
	}
	
	public static FutureRestlt getFuture(String requesNo){
		return caches.get(requesNo);
	}
	
	public static void removeFuture(String requesNo){
		caches.remove(requesNo);
	}
}
