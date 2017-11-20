package com.fl.postgmdprocessor.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.engineprocessor.engine.ExecutorPool;
import com.fl.engineprocessor.thread.InputExecutorThread;
import com.fl.postgmdprocessor.dao.PostGMDProcessorDAO;

@Component
public class PostGMDInputExecutorThread extends InputExecutorThread<PostGMDProcessorThread>{

	@Autowired
	private PostGMDProcessorDAO dao;
	
	public PostGMDInputExecutorThread(ExecutorPool<PostGMDProcessorThread> processorPool) {
		super(processorPool);
	}

	@Override
	protected void loadQueue() {
		
		dao.getNewEntries();
		
		
	}
	

}
