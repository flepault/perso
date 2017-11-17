package com.fl.massengineprocessor.thread;

import org.apache.log4j.Logger;

import com.fl.lib.engine.ExecutorPool;
import com.fl.lib.thread.ProcessorThread;

public class MassEngineProcessorThread extends ProcessorThread{

	private final static Logger logger = Logger.getLogger(MassEngineProcessorThread.class);	

	protected ExecutorPool<MassEngineProcessorThread> processorPool;

	public MassEngineProcessorThread(ExecutorPool<MassEngineProcessorThread> massEngineProcessorPool,long getThreadSleepTime,long getThreadShutdownCounter) {
		super(getThreadSleepTime,getThreadShutdownCounter);
		this.processorPool = massEngineProcessorPool;
		//this.dao = new MassEngineProcessorDAO();
	}

	@Override
	synchronized protected void processRequest() {
		logger.debug(request);

		String[] fields = request.split("\\,");

		logger.debug(this.getName()+": Fields [ND= " + fields[0] + " , Numero Compte=" + fields[1] + " , Rateplan=" + fields[2] + " , Formules=" + fields[3] + " , débit=" + fields[4] + " , durée=" + fields[5]+  "]");

		//dao.changeTM(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), Integer.parseInt(fields[2]), fields[3]);
		
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
