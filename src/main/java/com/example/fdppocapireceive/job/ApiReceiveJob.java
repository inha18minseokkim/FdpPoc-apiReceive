package com.example.fdppocapireceive.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class ApiReceiveJob {
    @Value("${PROCESS_DATE}")
    private String processDate;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ReceiveTasklet receiveTasklet;

    @Bean
    public JobExecution jobExecution(JobRepository jobRepository,PlatformTransactionManager transactionManager) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        return dailyJobLauncher(jobRepository).run(
            dailyReceiveJob(jobRepository,transactionManager),jobParameters()
        );
    }
    @Bean
    public JobLauncher dailyJobLauncher(JobRepository jobRepository){
        TaskExecutorJobLauncher taskExecutorJobLauncher = new TaskExecutorJobLauncher();
        taskExecutorJobLauncher.setJobRepository(jobRepository);
        taskExecutorJobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return taskExecutorJobLauncher;
    }

    @Bean
    public JobParameters jobParameters() {
        return new JobParametersBuilder()
                .addString("processDate",processDate)
                .addLong("executeDateTime", System.currentTimeMillis())
                .toJobParameters();
    }

    @Bean
    public Job dailyReceiveJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        Job job = new JobBuilder("dailyReceiveJob",jobRepository)
                .start(dailyReceiveStep()).build();
        return job;
    }

    @Bean
    public Step dailyReceiveStep() {
        return new StepBuilder("dailyReceiveStep",jobRepository)
                .tasklet(receiveTasklet,transactionManager).build();
    }



}
