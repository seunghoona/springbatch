package com.inflearn.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ExecutionContextConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job exJob() {
        return this.jobBuilderFactory.get("exJob")
                .start(exStep1())
                .next(exStep2())
                .next(exStep3())
                .next(exStep4())
                .build();
    }

    @Bean
    public Step exStep1() {
        return stepBuilderFactory.get("exStep1")
                .tasklet((contribution, steChunkContext)->{
                    System.out.println("this.step1().getName() = " + this.exStep1().getName());
                    ExecutionContext jobExecutionContext = contribution.getStepExecution().getJobExecution().getExecutionContext();
                    ExecutionContext stepExecutionContext = contribution.getStepExecution().getExecutionContext();

                    String jobName = steChunkContext.getStepContext().getStepExecution().getJobExecution().getJobInstance().getJobName();
                    String stepName = steChunkContext.getStepContext().getStepExecution().getStepName();

                    if (jobExecutionContext.get("jobName") == null) {
                        jobExecutionContext.put("jobName", jobName);
                    }

                    if (jobExecutionContext.get("stepName") == null) {
                        stepExecutionContext.put("stepName", stepName);
                    }
                    System.out.println("jobName" + jobExecutionContext.get("jobName"));
                    System.out.println("stepName" + stepExecutionContext.get("stepName"));
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    @Bean
    public Step exStep2() {
        return stepBuilderFactory.get("exStep2")
                .tasklet((contribution, steChunkContext)->{
                    ExecutionContext jobExecutionContext = contribution.getStepExecution().getJobExecution().getExecutionContext();
                    ExecutionContext stepExecutionContext = contribution.getStepExecution().getExecutionContext();

                    System.out.println("jobName" + jobExecutionContext.get("jobName"));
                    System.out.println("stepName 은 공유 불가능하기에 null" + stepExecutionContext.get("stepName"));
                    String stepName = steChunkContext.getStepContext().getStepExecution().getStepName();

                    if (jobExecutionContext.get("stepName") == null) {
                        stepExecutionContext.put("stepName", stepName);
                    }

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    @Bean
    public Step exStep3() {
        return stepBuilderFactory.get("exStep3")
                .tasklet((contribution, steChunkContext)->{
                    System.out.println("this.step3().getName() = " + this.exStep3().getName());
                    System.out.println("실패하는 단계에서 해당 단계부터 시작하면서 새롭게 시작할 수 있는지");
                    Object name = steChunkContext.getStepContext().getStepExecution().getExecutionContext().get("name");


                    if (name == null) {
                        steChunkContext.getStepContext().getStepExecution().getExecutionContext().put("name", "user1");
                        throw
                                new IllegalArgumentException("step3 was failed");
                    }

                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    @Bean
    public Step exStep4() {
        return stepBuilderFactory.get("step4")
                .tasklet((contribution, steChunkContext)->{
                    System.out.println("this.step4().getName() = " + this.exStep4().getName());
                    Object name = steChunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("name");
                    System.out.println("name = " + name);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}