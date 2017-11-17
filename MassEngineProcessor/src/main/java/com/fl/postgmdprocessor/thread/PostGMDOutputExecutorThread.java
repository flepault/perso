package com.fl.postgmdprocessor.thread;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fl.engineprocessor.thread.OutputExecutorThread;

@Component
public class PostGMDOutputExecutorThread  extends OutputExecutorThread{

	private static Logger logger = Logger.getLogger(PostGMDOutputExecutorThread.class.getName());

	public PostGMDOutputExecutorThread(){
		super();	
	}
	
	@Override
	protected void processRequest(String request) {
		logger.debug(request);		
	}

}