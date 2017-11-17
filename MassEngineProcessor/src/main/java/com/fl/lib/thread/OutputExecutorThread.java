package com.fl.lib.thread;

import java.util.Stack;

import org.springframework.beans.factory.annotation.Value;

import com.fl.lib.engine.FileManagment;


public abstract class OutputExecutorThread  extends Thread{

	private volatile Stack<String> queue;

	@Value("${output.thread.sleep.time}")
	public int getOutputThreadSleepTime;
	

	public OutputExecutorThread(){
		super("Output");
		queue = new Stack<String>();
    }
	
	@Override
	public void run() {	

		while(true){

			FileManagment.loadOutputFile(queue);

			if(queue.isEmpty()){				
				try { Thread.sleep(getOutputThreadSleepTime); }
				catch (InterruptedException e) {}						
			}else{

				while(!queue.isEmpty()){

					String request = removeElement();
					
					processRequest(request);
					
					FileManagment.deleteFile(request);

				}
			}
		}


	}
	
	

	protected abstract void processRequest(String request);

	synchronized private String removeElement(){
		return (String)queue.pop();
	}

	synchronized public void pushElement(String request){
		queue.push(request);
	}

}
