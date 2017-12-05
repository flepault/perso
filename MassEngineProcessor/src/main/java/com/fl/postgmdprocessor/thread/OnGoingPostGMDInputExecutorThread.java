package com.fl.postgmdprocessor.thread;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private final static Logger logger = LoggerFactory.getLogger(OnGoingPostGMDInputExecutorThread.class);		
		
	@Autowired
	@Qualifier("PostGMDProcessorDAO")
	private PostGMDProcessorDAO dao;

	public OnGoingPostGMDInputExecutorThread(ExecutorPool<PostGMDProcessorThread> processorPool) {
		super(processorPool);
	}

	@Override
	protected void loadQueue() {		
		
		
		List<String> list = dao.getNewEntries();
		
		if(list!=null)
			logger.info("Get new set of "+list.size()+" entries");
		
		for(String str:list)
			FileManagment.createFile(str);
		
		logger.info("Entries stored");

		pushListElement(list);

	}

	@Override
	protected PostGMDProcessorThread getInstanceOfT() {
		return new PostGMDProcessorThread(processorPool, getThreadSleepTime, getThreadShutdownCounter,dao);
	}

	

}
