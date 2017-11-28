package com.fl.engineprocessor.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fl.engineprocessor.engine.ExecutorPool;
import com.fl.engineprocessor.engine.FileManagment;

public abstract class ProcessorThread extends Thread{

	private final static Logger logger = LoggerFactory.getLogger(ProcessorThread.class);	

	volatile protected Boolean close = false;

	volatile protected String request = null;
	
	private boolean run = true;

	private int count=0;	

	private long getThreadSleepTime;
	private long getThreadShutdownCounter;
	
	@SuppressWarnings("rawtypes")
	protected ExecutorPool processorPool;
	
	public ProcessorThread(long getThreadSleepTime,long getThreadShutdownCounter,@SuppressWarnings("rawtypes") ExecutorPool processorPool) {
		super("ProcessorThread-");
		this.setName(this.getName()+this.getId());
		this.getThreadSleepTime = getThreadSleepTime;
		this.getThreadShutdownCounter = getThreadShutdownCounter;
		logger.info(this.getName()+" : launched");
		this.processorPool = processorPool;
	}	

	@Override
	public void run() {

		while(run){

			if(request == null){

				if(count>=getThreadShutdownCounter){					
					shutdown();
				}else{
					try {						
						increase();
						Thread.sleep(getThreadSleepTime); 
					}
					catch (InterruptedException e) {} 
				}
			}else{

				reset();

				processRequest();
				
				FileManagment.renameFile(request);				

				pushInstanceToStack();

			}

		}
	}
	
	@SuppressWarnings("unchecked")
	synchronized protected void removeFromPool() {
		processorPool.removeInstanceToStack(this);
	}

	@SuppressWarnings("unchecked")
	synchronized public void pushToPool() {
		processorPool.pushInstanceToStack(this);		
	}

	
	private void  shutdown(){		
		removeFromPool();
		run = false;
		logger.info(this.getName()+" : shutdowned");		
	}


	private void pushInstanceToStack() {
		this.cleanRequest();
		pushToPool();
	}
	

	private void reset() {
		count=0;
	}

	private void increase() {
		count++;		
	}

	synchronized public void close() {
		this.close = true;
	}

	synchronized public String getRequest() {
		return request;
	}

	synchronized public void setRequest(String request) {
		this.request = request;
	}

	synchronized public void cleanRequest() {
		this.request = null;
	}


	protected abstract void processRequest() ;
	
}
