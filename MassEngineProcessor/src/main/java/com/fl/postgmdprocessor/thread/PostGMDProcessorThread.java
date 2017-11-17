package com.fl.postgmdprocessor.thread;

import org.apache.log4j.Logger;

import com.fl.engineprocessor.engine.ExecutorPool;
import com.fl.engineprocessor.thread.ProcessorThread;
import com.fl.postgmdprocessor.dao.PostGMDProcessorDAO;

public class PostGMDProcessorThread extends ProcessorThread{

	private final static Logger logger = Logger.getLogger(PostGMDProcessorThread.class);	

	protected ExecutorPool<PostGMDProcessorThread> processorPool;
	
	private PostGMDProcessorDAO dao;

	public PostGMDProcessorThread(ExecutorPool<PostGMDProcessorThread> massEngineProcessorPool,long getThreadSleepTime,long getThreadShutdownCounter) {
		super(getThreadSleepTime,getThreadShutdownCounter);
		this.processorPool = massEngineProcessorPool;
		this.dao = new PostGMDProcessorDAO();
	}

	@Override
	synchronized protected void processRequest() {
		logger.debug(request);
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
