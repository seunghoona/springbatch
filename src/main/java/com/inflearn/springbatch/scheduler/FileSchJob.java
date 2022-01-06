package com.inflearn.springbatch.scheduler;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileSchJob extends QuartzJobBean {

    private final Job fileJob;
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        final String requestDate = toRequestDate(context);

        validIsStartedBatch(requestDate);

        jobLauncher.run(fileJob, createJobParameters(requestDate));
    }

    private String toRequestDate(JobExecutionContext context) {
        return context.getJobDetail()
            .getJobDataMap()
            .get("requestDate").toString();
    }

    private JobParameters createJobParameters(final String requestDate) {

        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("id", new Date().getTime())
            .addString("requestDate", requestDate)
            .toJobParameters();

        return jobParameters;
    }

    private void validIsStartedBatch(final String requestDate) throws NoSuchJobException {
        final String jobName = fileJob.getName();
        int jobInstanceCount = jobExplorer.getJobInstanceCount(jobName);

        List<JobInstance> jobInstances = jobExplorer.getJobInstances(jobName, 0, jobInstanceCount);

        if (!jobInstances.isEmpty()) {
            checkedJob(jobInstances, requestDate);
        }

    }

    private void checkedJob(List<JobInstance> jobInstances, String requestDate) {
        for (JobInstance jobInstance : jobInstances) {
            validAlreadyExecution(requestDate, jobInstance);
        }
    }

    private void validAlreadyExecution(String requestDate, JobInstance jobInstance) {
        List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance)
            .stream()
            .filter(sameRequestDate(requestDate))
            .collect(Collectors.toList());

        if (!jobExecutions.isEmpty()) {
            try {
                throw new JobExecutionException(requestDate + " already exists");
            } catch (JobExecutionException e) {
                throw new com.inflearn.springbatch.scheduler.exception.JobExecutionException(e.getMessage());
            }
        }
    }

    private Predicate<JobExecution> sameRequestDate(String requestDate) {
        return jobExecution -> jobExecution.getJobParameters().getString("requestDate")
            .equals(requestDate);
    }
}
