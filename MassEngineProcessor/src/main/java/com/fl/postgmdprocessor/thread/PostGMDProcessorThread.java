package com.fl.postgmdprocessor.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fl.engineprocessor.engine.ExecutorPool;
import com.fl.engineprocessor.thread.ProcessorThread;
import com.fl.postgmdprocessor.dao.PostGMDProcessorDAO;

public class PostGMDProcessorThread extends ProcessorThread{

	private final static Logger logger = LoggerFactory.getLogger(PostGMDProcessorThread.class);	

	protected ExecutorPool<PostGMDProcessorThread> processorPool;
	
	private PostGMDProcessorDAO dao ;

	public PostGMDProcessorThread(ExecutorPool<PostGMDProcessorThread> massEngineProcessorPool,long getThreadSleepTime,long getThreadShutdownCounter,PostGMDProcessorDAO dao) {
		super(getThreadSleepTime,getThreadShutdownCounter);
		this.processorPool = massEngineProcessorPool;
		this.dao = dao;
	}

	@Override
	synchronized protected void processRequest() {
		logger.debug(this.getName()+":"+request);
		
		dao.singlePostGMD(request);
			
	}

	@Override
	synchronized protected void removeFromPool() {
		processorPool.removeInstanceToStack(this);
	}

	@Override
	synchronized public void pushToPool() {
		processorPool.pushInstanceToStack(this);		
	}

}
