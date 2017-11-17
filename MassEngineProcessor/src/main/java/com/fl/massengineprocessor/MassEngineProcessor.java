package com.fl.massengineprocessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.fl.lib.engine.ExecutorPool;
import com.fl.massengineprocessor.thread.MassEngineInputExecutorThread;
import com.fl.massengineprocessor.thread.MassEngineOutputExecutorThread;
import com.fl.massengineprocessor.thread.MassEngineProcessorThread;

@SpringBootApplication
@EnableAutoConfiguration
public class MassEngineProcessor {

	@Autowired
	private MassEngineInputExecutorThread inputExecutorThread; 
	
	@Autowired
	private MassEngineOutputExecutorThread outputExecutorThread; 
		
	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}
	
	@Bean
	public ExecutorPool<MassEngineProcessorThread> executorPool(@Value("${pool.size.max.value}")Integer maxActiveThreads){
		return new ExecutorPool<MassEngineProcessorThread>(maxActiveThreads);
	}

	@Bean
	public CommandLineRunner schedulingRunner(final TaskExecutor executor) {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				executor.execute(inputExecutorThread);
				executor.execute(outputExecutorThread);
			}
		};

	}
	
	public static void main(String[] args) {
		SpringApplication.run(MassEngineProcessor.class, args);				
	}
}
