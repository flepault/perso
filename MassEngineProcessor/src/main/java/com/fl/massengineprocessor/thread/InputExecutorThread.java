package com.fl.massengineprocessor.thread;

import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fl.massengineprocessor.engine.ExecutorPool;
import com.fl.massengineprocessor.engine.ExecutorThread;
import com.fl.massengineprocessor.engine.FileManagment;

@Component
public class InputExecutorThread extends Thread{
	
	//private static Logger logger = Logger.getLogger(InputExecutorThread.class.getName());

	@Value("${input.thread.sleep.time}")
	public int getInputThreadSleepTime;

	@Value("${pool.sleep.time}")
	public long getPoolSleepTime;	

	@Value("${thread.sleep.time}")
	public long getThreadSleepTime;

	@Value("${thread.shutdown.counter}")
	public long getThreadShutdownCounter;

	private volatile Stack<String> queue;

	@Autowired
	private ExecutorPool<MassEngineProcessorThread> massEngineProcessorPool;

	volatile protected Boolean close = false;	

	public InputExecutorThread(){
		super("Input");
		queue = new Stack<String>();
	}

	@Override
	public void run() {

		FileManagment.loadInputFile(queue);

		while(true){

			if(massEngineProcessorPool.isPoolUnavailable()){
				try { Thread.sleep(getPoolSleepTime); }
				catch (InterruptedException e) {}
			}else {

				if(queue.isEmpty()){				
					try { Thread.sleep(getInputThreadSleepTime); }
					catch (InterruptedException e) {}						
				}else{			
					String request = removeElement();

					if(massEngineProcessorPool.isInstanceStackEmpty())
						massEngineProcessorPool.addNewInstanceToStack(new MassEngineProcessorThread(massEngineProcessorPool,getThreadSleepTime,getThreadShutdownCounter));

					ExecutorThread massEngineProcessorThread = massEngineProcessorPool.popInstanceFromStack();
					massEngineProcessorThread.setRequest(request);
				}
			}
		}

	}

	synchronized private String removeElement(){
		return (String)queue.pop();
	}

	synchronized public void pushElement(String request){
		queue.push(request);
	}
}
