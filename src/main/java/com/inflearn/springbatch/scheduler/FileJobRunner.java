package com.inflearn.springbatch.scheduler;

import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileJobRunner extends JobRunner {

    private final Scheduler scheduler;

    @Override
    protected void doRun(ApplicationArguments args) {

        String[] sourceArgs = args.getSourceArgs();



        JobDetail jobDetail = buildJobDetail(FileSchJob.class, "fileJob", "batch", new HashMap());
        jobDetail.getJobDataMap()
            .put("requestDate", sourceArgs[0]);
        Trigger trigger = buildJobTrigger("0/50 * * * * ?");

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}
