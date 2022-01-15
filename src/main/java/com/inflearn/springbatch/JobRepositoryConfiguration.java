package com.inflearn.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobRepositoryConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobExecutionListener jobExecutionListener;

    @Bean
    public Job jrJob() {
        return this.jobBuilderFactory.get("jrJob")
                .start(JobRepositoryStep1())
                .next(JobRepositoryStep2())
                .listener(jobExecutionListener)
                .build();
    }

    @Bean
    public Step JobRepositoryStep1() {
        return stepBuilderFactory.get("JobRepositoryStep1")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(JobRepositoryStep1().getName());
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step JobRepositoryStep2() {
        return stepBuilderFactory.get("JobRepositoryStep2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(JobRepositoryStep2().getName());
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
