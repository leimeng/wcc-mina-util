package com.sj.wcc.protocal;

import java.io.Serializable;

public class WccRequest extends WccTransferObj implements Serializable{
	private static final long serialVersionUID = 4634046963464305013L;
	private String requesNo;
	private String inFace;
	private String mehtod;
	private Class<?>[] paramType;
	private Object[] paramValue;
	
	public String getInFace() {
		return inFace;
	}
	public void setInFace(String inFace) {
		this.inFace = inFace;
	}
	public String getMehtod() {
		return mehtod;
	}
	public void setMehtod(String mehtod) {
		this.mehtod = mehtod;
	}
	public Class<?>[] getParamType() {
		return paramType;
	}
	public void setParamType(Class<?>[] paramType) {
		this.paramType = paramType;
	}
	public Object[] getParamValue() {
		return paramValue;
	}
	public void setParamValue(Object[] paramValue) {
		this.paramValue = paramValue;
	}
	public String getRequesNo() {
		return requesNo;
	}
	public void setRequesNo(String requesNo) {
		this.requesNo = requesNo;
	}
}
