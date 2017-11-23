package com.fl.postgmdprocessor.thread;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.fl.engineprocessor.engine.ExecutorPool;
import com.fl.engineprocessor.thread.InputExecutorThread;
import com.fl.postgmdprocessor.dao.PostGMDProcessorDAO;

@Component
@Profile("daily")
public class DailyPostGMDInputExecutorThread extends InputExecutorThread<PostGMDProcessorThread>{

	@Autowired
	@Qualifier("PostGMDProcessorDAO")
	private PostGMDProcessorDAO dao;

	public DailyPostGMDInputExecutorThread(ExecutorPool<PostGMDProcessorThread> processorPool) {
		super(processorPool);
	}

	@Override
	protected void loadQueue() {		
		
		List<String> requestList = dao.getNewEntries();
		
		if(requestList==null || requestList.size()==0)
			close=true;

		pushListElement(requestList);

	}

	@Override
	protected PostGMDProcessorThread getInstanceOfT() {
		return new PostGMDProcessorThread(processorPool, getThreadSleepTime, getThreadShutdownCounter,dao);
	}

	

}
