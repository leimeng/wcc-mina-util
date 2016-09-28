package com.sj.wcc.task;

public abstract class TaskImp implements Runnable{
	
	/**default false*/
	protected boolean joinMonFlag = false;
	
	protected abstract void routine();

	public boolean isJoinMonFlag() {
		return joinMonFlag;
	}

	public void setJoinMonFlag(boolean joinMonFlag) {
		this.joinMonFlag = joinMonFlag;
	}
	
}
