package com.sj.wcc.disposer;

import org.apache.mina.core.session.IoSession;

import com.sj.wcc.protocal.WccRequest;

public class WccRequestDisposer implements WccIDisposer{
	IoSession session;

	public WccRequestDisposer(IoSession session) {
		this.session = session;
	}

	@Override
	public FutureRestlt sendRequset(WccRequest request) {
		FutureRestlt future = new FutureRestlt();
		session.write(request);
		return future;
	}
}
