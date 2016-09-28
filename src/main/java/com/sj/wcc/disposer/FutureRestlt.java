package com.sj.wcc.disposer;

public class FutureRestlt extends Result{
	Result result;
	boolean completed = false;
	
	public synchronized void setResult(Result result){
		this.result = result;
		this.completed = true;
		this.notifyAll();
	}
	
	@Override
	public synchronized Object getResultValue() {
		if(!this.completed){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return result.getResultValue();
	}
}
