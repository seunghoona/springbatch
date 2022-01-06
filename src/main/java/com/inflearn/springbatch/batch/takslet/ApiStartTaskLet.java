package com.inflearn.springbatch.batch.takslet;

import static org.springframework.batch.repeat.RepeatStatus.FINISHED;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class ApiStartTaskLet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
        throws Exception {
        System.out.println(" >> API service is STARTED ");
        return FINISHED;
    }
}
