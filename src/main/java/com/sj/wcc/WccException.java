package com.sj.wcc;

public class WccException extends RuntimeException{
	private static final long serialVersionUID = 5380360052245399125L;
	private String msg;
	
	public WccException(String msg) {
		super(msg);
		this.msg = msg;
	}
}
