package com.fl.massengineprocessor.engine;

public class ExecutorThread extends Thread{

	volatile protected Boolean close = false;
	
	volatile protected String request = null;
	
	public ExecutorThread() 
	{
		super("ExecutorInstance-");
		this.setName(this.getName()+this.getId());
			
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
}
