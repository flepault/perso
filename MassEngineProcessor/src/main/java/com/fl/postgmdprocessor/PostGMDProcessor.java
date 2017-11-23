package com.fl.postgmdprocessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.fl.engineprocessor.engine.ExecutorPool;
import com.fl.engineprocessor.thread.InputExecutorThread;
import com.fl.postgmdprocessor.dao.DailyPostGMDProcessorDAO;
import com.fl.postgmdprocessor.dao.OnGoingPostGMDProcessorDAO;
import com.fl.postgmdprocessor.dao.PostGMDProcessorDAO;
import com.fl.postgmdprocessor.thread.PostGMDOutputExecutorThread;
import com.fl.postgmdprocessor.thread.PostGMDProcessorThread;

@SpringBootApplication
@EnableAutoConfiguration
public class PostGMDProcessor {

	@Autowired
	private InputExecutorThread<PostGMDProcessorThread> inputExecutorThread; 
	
	@Autowired
	private PostGMDOutputExecutorThread outputExecutorThread; 
	
		
	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}
	
	@Bean
	public ExecutorPool<PostGMDProcessorThread> executorPool(@Value("${pool.size.max.value}")Integer maxActiveThreads){
		return new ExecutorPool<PostGMDProcessorThread>(maxActiveThreads);
	}

	@Bean
	@Profile("ongoing")
	public CommandLineRunner schedulingRunnerOnGoing(final TaskExecutor executor) {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				executor.execute(inputExecutorThread);
				executor.execute(outputExecutorThread);
			}
		};

	}
	
	@Bean
	@Profile("daily")
	public CommandLineRunner schedulingRunnerDaily(final TaskExecutor executor) {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				inputExecutorThread.run();
			}
		};

	}
	
	
	@Bean("PostGMDProcessorDAO")
	@Profile("daily")
	public PostGMDProcessorDAO getDailyPostGMDProcessorDAO(){
	
		return new DailyPostGMDProcessorDAO();
		
	}
	
	@Bean("PostGMDProcessorDAO")
	@Profile("ongoing")
	public PostGMDProcessorDAO getOnGoingPostGMDProcessorDAO(){
			
		return new OnGoingPostGMDProcessorDAO();
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(PostGMDProcessor.class, args);				
	}
}
