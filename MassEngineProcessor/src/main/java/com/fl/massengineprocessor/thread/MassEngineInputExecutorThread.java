package com.fl.massengineprocessor.thread;

import org.springframework.stereotype.Component;

import com.fl.engineprocessor.engine.ExecutorPool;
import com.fl.engineprocessor.thread.InputExecutorThread;

@Component
public class MassEngineInputExecutorThread extends InputExecutorThread<MassEngineProcessorThread>{

	public MassEngineInputExecutorThread(ExecutorPool<MassEngineProcessorThread> processorPool) {
		super(processorPool);
	}

	@Override
	protected void loadQueue() {
		//Not necessary;
	}
	

}
