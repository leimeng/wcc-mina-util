package com.sj.wcc.disposer;

public class RealResult extends Result{

	Object object;
	
	public RealResult(Object object) {
		this.object = object;
	}

	@Override
	protected Object getResultValue() {
		return this.object;
	}
}
