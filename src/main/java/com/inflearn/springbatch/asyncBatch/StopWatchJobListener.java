package com.inflearn.springbatch.asyncBatch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class StopWatchJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        long l = jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime();

        System.out.println("총소요시간: = " +  l );
    }
}
