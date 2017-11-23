package com.fl.postgmdprocessor.thread;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fl.engineprocessor.engine.ExecutorPool;
import com.fl.engineprocessor.engine.FileManagment;
import com.fl.engineprocessor.thread.InputExecutorThread;
import com.fl.postgmdprocessor.dao.PostGMDProcessorDAO;

@Component
@Profile("ongoing")
public class OnGoingPostGMDInputExecutorThread extends InputExecutorThread<PostGMDProcessorThread>{

	@Autowired
	@Qualifier("PostGMDProcessorDAO")
	private PostGMDProcessorDAO dao;

	public OnGoingPostGMDInputExecutorThread(ExecutorPool<PostGMDProcessorThread> processorPool) {
		super(processorPool);
	}

	@Override
	protected void loadQueue() {		
		
		List<String> list = dao.getNewEntries();
		
		for(String str:list)
			FileManagment.createFile(str);

		pushListElement(list);

	}

	@Override
	protected PostGMDProcessorThread getInstanceOfT() {
		return new PostGMDProcessorThread(processorPool, getThreadSleepTime, getThreadShutdownCounter,dao);
	}

	

}
