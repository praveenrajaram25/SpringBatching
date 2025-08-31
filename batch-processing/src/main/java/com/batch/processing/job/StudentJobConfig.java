package com.batch.processing.job;

import com.batch.processing.model.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;

@Configuration
public class StudentJobConfig {

	@Bean
	Job studentJob(JobRepository jobRepository, Step studentStep) {
		return new JobBuilder("studentJob", jobRepository)
				.start(studentStep)
				.build();
	}

	@Bean
	Step studentStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			ItemReader<Student> reader, ItemProcessor<Student, Student> processor, ItemWriter<Student> writer) {

		return new StepBuilder("studentStep", jobRepository)
				.<Student, Student>chunk(10000, transactionManager)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.taskExecutor(taskExecutor())
				.build();
	}
	
	@Bean
	TaskExecutor taskExecutor() {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(10);  // max 10 concurrent threads
	    executor.setMaxPoolSize(10);
	    executor.setQueueCapacity(25);
	    executor.setThreadNamePrefix("batch-thread-");
	    executor.initialize();
	    return executor;
	}

}
