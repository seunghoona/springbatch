package com.inflearn.springbatch.batch.listener;

import org.springframework.batch.core.JobExecution;

public class JobListener implements
    org.springframework.batch.core.JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        long l = jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime();
        System.out.println("총 소요시간  = " + l);
    }
}
