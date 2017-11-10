package com.fl.massengineprocessor.thread;

import org.apache.log4j.Logger;

import com.fl.massengineprocessor.engine.ExecutorPool;
import com.fl.massengineprocessor.engine.ExecutorThread;
import com.fl.massengineprocessor.engine.FileManagment;

public class MassEngineProcessorThread extends ExecutorThread{
	
	
	private final static Logger logger = Logger.getLogger(MassEngineProcessorThread.class);	
	
	private ExecutorPool<MassEngineProcessorThread> massEngineProcessorPool;
		
	private long getThreadSleepTime;
	private long getThreadShutdownCounter;

	public MassEngineProcessorThread(ExecutorPool<MassEngineProcessorThread> massEngineProcessorPool,long getThreadSleepTime,long getThreadShutdownCounter) {
		super();
		this.massEngineProcessorPool = massEngineProcessorPool;
		this.getThreadSleepTime = getThreadSleepTime;
		this.getThreadShutdownCounter = getThreadShutdownCounter;
		logger.info(this.getName()+" : launched");
		//this.dao = new MassEngineProcessorDAO();
	}
	
	protected void shutdown() {
		massEngineProcessorPool.removeInstanceToStack(this);
		run = false;
		logger.info(this.getName()+" : shutdowned");
	}	

	boolean run = true;

	private int count=0;

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
				
				logger.debug(request);

				String[] fields = request.split("\\,");

				logger.debug(this.getName()+": Fields [ND= " + fields[0] + " , Numero Compte=" + fields[1] + " , Rateplan=" + fields[2] + " , Formules=" + fields[3] + " , débit=" + fields[4] + " , durée=" + fields[5]+  "]");
				
				//dao.changeTM(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), Integer.parseInt(fields[2]), fields[3]);
				
				FileManagment.renameFile(request);				
				
				massEngineProcessorPool.pushInstanceToStack(this);
				

			}
			
		}
	}

	private void reset() {
		count=0;
	}

	private void increase() {
		count++;		
	}

}
