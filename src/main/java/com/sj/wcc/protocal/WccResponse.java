package com.sj.wcc.protocal;

import java.io.Serializable;

public class WccResponse extends WccTransferObj implements Serializable{
	private static final long serialVersionUID = -1677988686985456656L;
	private String requesNo;
	private Object returnValue;
	public String getRequesNo() {
		return requesNo;
	}
	public void setRequesNo(String requesNo) {
		this.requesNo = requesNo;
	}
	public Object getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}
	
}
