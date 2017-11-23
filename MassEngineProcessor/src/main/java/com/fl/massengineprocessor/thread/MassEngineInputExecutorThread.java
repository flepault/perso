package com.fl.massengineprocessor.thread;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fl.engineprocessor.engine.ExecutorPool;
import com.fl.engineprocessor.thread.InputExecutorThread;

@Component
@Profile("massengineprocessor")
public class MassEngineInputExecutorThread extends InputExecutorThread<MassEngineProcessorThread>{

	public MassEngineInputExecutorThread(ExecutorPool<MassEngineProcessorThread> processorPool) {
		super(processorPool);
	}

	@Override
	protected void loadQueue() {
		//Not necessary;
	}

	@Override
	protected MassEngineProcessorThread getInstanceOfT() {
		return new MassEngineProcessorThread(processorPool, getThreadSleepTime, getThreadShutdownCounter);
	}
	

}
