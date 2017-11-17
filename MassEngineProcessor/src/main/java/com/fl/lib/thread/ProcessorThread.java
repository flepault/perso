package com.fl.lib.thread;

import org.apache.log4j.Logger;

import com.fl.lib.engine.FileManagment;

public abstract class ProcessorThread extends Thread{

	private final static Logger logger = Logger.getLogger(ProcessorThread.class);	

	volatile protected Boolean close = false;

	volatile protected String request = null;
	
	private boolean run = true;

	private int count=0;	

	private long getThreadSleepTime;
	private long getThreadShutdownCounter;

	public ProcessorThread(long getThreadSleepTime,long getThreadShutdownCounter) {
		super("ExecutorInstance-");
		this.setName(this.getName()+this.getId());
		this.getThreadSleepTime = getThreadSleepTime;
		this.getThreadShutdownCounter = getThreadShutdownCounter;
		logger.info(this.getName()+" : launched");
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

	
	private void  shutdown(){		
		removeFromPool();
		run = false;
		logger.info(this.getName()+" : shutdowned");		
	}

	protected abstract void removeFromPool();

	private void pushInstanceToStack() {
		this.cleanRequest();
		pushToPool();
	}
	
	protected abstract void pushToPool();

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
