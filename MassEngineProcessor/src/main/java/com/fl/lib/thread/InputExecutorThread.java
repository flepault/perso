package com.fl.lib.thread;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Value;

import com.fl.lib.engine.ExecutorPool;
import com.fl.lib.engine.FileManagment;

public class InputExecutorThread<T extends ProcessorThread> extends Thread{
	
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

	private ExecutorPool<T> processorPool;

	volatile protected Boolean close = false;	

	public InputExecutorThread(ExecutorPool<T> processorPool){
		super("Input");
		this.queue = new Stack<String>();
		this.processorPool = processorPool;
	}

	@Override
	public void run() {

		FileManagment.loadInputFile(queue);

		while(true){

			if(processorPool.isPoolUnavailable()){
				try { Thread.sleep(getPoolSleepTime); }
				catch (InterruptedException e) {}
			}else {

				if(queue.isEmpty()){				
					try { Thread.sleep(getInputThreadSleepTime); }
					catch (InterruptedException e) {}						
				}else{			
					String request = removeElement();

					if(processorPool.isInstanceStackEmpty())
						processorPool.addNewInstanceToStack(getInstanceOfT());

					T processorThread = processorPool.popInstanceFromStack();
					processorThread.setRequest(request);
				}
			}
		}

	}
	
	private T getInstanceOfT()
    {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<T> type = (Class<T>) superClass.getActualTypeArguments()[0];
        try
        {
        	Constructor<T> constructor = type.getConstructor(ExecutorPool.class,long.class,long.class);
            return constructor.newInstance(processorPool,getThreadSleepTime,getThreadShutdownCounter);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

	synchronized private String removeElement(){
		return (String)queue.pop();
	}

	synchronized public void pushElement(String request){
		queue.push(request);
	}
}
