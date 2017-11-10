package com.fl.massengineprocessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.fl.massengineprocessor.thread.InputExecutorThread;
import com.fl.massengineprocessor.thread.OutputExecutorThread;

@SpringBootApplication
@EnableAutoConfiguration
public class MassEngineProcessor {

	@Autowired
	private InputExecutorThread inputExecutorThread; 
	
	@Autowired
	private OutputExecutorThread outputExecutorThread; 
		
	@Bean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
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
