package com.fl.postgmdprocessor.thread;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private final static Logger logger = LoggerFactory.getLogger(DailyPostGMDInputExecutorThread.class);
	
	@Autowired
	@Qualifier("PostGMDProcessorDAO")
	private PostGMDProcessorDAO dao;

	public DailyPostGMDInputExecutorThread(ExecutorPool<PostGMDProcessorThread> processorPool) {
		super(processorPool);		
	}
	
	@PostConstruct
	public void init(){
		dao.init();
	}

	@Override
	protected void loadQueue() {		
		
		List<String> requestList = dao.getNewEntries();
		
		if(requestList!=null)
			logger.info("Get new set of "+requestList.size()+" entries");
		
		if(requestList==null || requestList.size()==0)
			close=true;
		else
			pushListElement(requestList);

	}

	@Override
	protected PostGMDProcessorThread getInstanceOfT() {
		return new PostGMDProcessorThread(processorPool, getThreadSleepTime, getThreadShutdownCounter,dao);
	}

	

}
