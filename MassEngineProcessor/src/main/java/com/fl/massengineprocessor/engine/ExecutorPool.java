package com.fl.massengineprocessor.engine;

import java.util.Stack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExecutorPool<T extends ExecutorThread> {

	volatile private Integer openInstance = 0;

	volatile private Stack<T> pool;
	private Integer maxActiveThreads;
	
	public ExecutorPool(@Value("${pool.size.max.value}")Integer maxActiveThreads)
	{
		this.pool = new Stack<T>();
		this.maxActiveThreads = maxActiveThreads;

	}
	
	public int size(){
		return pool.size();
	}
	
	synchronized public ExecutorThread popInstanceFromStack() {
		if (pool.isEmpty())
			return null;

		return pool.pop(); 
	}

	synchronized public void addNewInstanceToStack(T executorThread){
		openInstance++;
		executorThread.start();
		pool.push(executorThread);
	}

	synchronized public void removeInstanceToStack(ExecutorThread executorInstance){
		pool.remove(executorInstance);
		if( openInstance > 0 )
		{
			openInstance--;
		}
	}

	synchronized public void pushInstanceToStack(T executorInstance) {
		executorInstance.cleanRequest();
		pool.push(executorInstance);
	}

	synchronized public boolean isInstanceStackEmpty() {
		return pool.isEmpty();
	}

	synchronized public boolean isInstanceStackFull() {
		return pool.size() == maxActiveThreads;
	}

	synchronized public boolean isOpenInstanceFull() {
		return openInstance == maxActiveThreads;
	}

	synchronized public boolean isPoolUnavailable() {
		return isInstanceStackEmpty() && isOpenInstanceFull();
	}

	synchronized public Integer instanceStackAvailable(){
		return pool.size();
	}

	synchronized public Integer openInstanceAvailable(){
		return openInstance;
	}

	synchronized public void close() {
		for(int i=0;i<pool.size();i++){
			pool.get(i).close();
		}
	}

}
