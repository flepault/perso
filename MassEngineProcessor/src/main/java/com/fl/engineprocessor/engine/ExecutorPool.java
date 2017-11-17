package com.fl.engineprocessor.engine;

import java.util.Stack;

import org.springframework.stereotype.Component;

import com.fl.engineprocessor.thread.ProcessorThread;

@Component
public class ExecutorPool<T extends ProcessorThread> {

	volatile private Integer openInstance = 0;

	volatile private Stack<T> pool;
	private Integer maxActiveThreads;
	
	public ExecutorPool(Integer maxActiveThreads)
	{
		this.pool = new Stack<T>();
		this.maxActiveThreads = maxActiveThreads;

	}
	
	public int size(){
		return pool.size();
	}
	
	synchronized public T popInstanceFromStack() {
		if (pool.isEmpty())
			return null;

		return pool.pop(); 
	}

	synchronized public void addNewInstanceToStack(T executorThread){
		openInstance++;
		executorThread.start();
		pool.push(executorThread);
	}

	synchronized public void removeInstanceToStack(T executorInstance){
		pool.remove(executorInstance);
		if( openInstance > 0 )
		{
			openInstance--;
		}
	}

	synchronized public void pushInstanceToStack(T executorInstance) {
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
