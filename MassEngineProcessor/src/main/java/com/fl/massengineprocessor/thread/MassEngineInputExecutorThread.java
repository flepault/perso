package com.fl.massengineprocessor.thread;

import org.springframework.stereotype.Component;

import com.fl.lib.engine.ExecutorPool;
import com.fl.lib.thread.InputExecutorThread;

@Component
public class MassEngineInputExecutorThread extends InputExecutorThread<MassEngineProcessorThread>{

	public MassEngineInputExecutorThread(ExecutorPool<MassEngineProcessorThread> processorPool) {
		super(processorPool);
	}
	

}
