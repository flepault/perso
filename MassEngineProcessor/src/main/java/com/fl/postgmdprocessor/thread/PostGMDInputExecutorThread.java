package com.fl.postgmdprocessor.thread;

import org.springframework.stereotype.Component;

import com.fl.engineprocessor.engine.ExecutorPool;
import com.fl.engineprocessor.thread.InputExecutorThread;

@Component
public class PostGMDInputExecutorThread extends InputExecutorThread<PostGMDProcessorThread>{

	public PostGMDInputExecutorThread(ExecutorPool<PostGMDProcessorThread> processorPool) {
		super(processorPool);
	}
	

}
