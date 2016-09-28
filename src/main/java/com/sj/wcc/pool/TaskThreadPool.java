package com.sj.wcc.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.sj.wcc.task.TaskImp;

public class TaskThreadPool extends ThreadPoolExecutor {
	public TaskThreadPool(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	
	public void runInPool(Runnable task){
		this.execute(task);
	}
	
	@Override
	protected void beforeExecute(Thread arg0, Runnable arg1) {
		TaskImp sr = (TaskImp)arg1;
		if(sr.isJoinMonFlag()){
			
		}
		super.beforeExecute(arg0, arg1);
	}
}