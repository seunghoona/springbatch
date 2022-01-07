package com.inflearn.springbatch.asyncBatch;

import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@RequiredArgsConstructor
public class AsyncConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("batchJob")
            .incrementer(new RunIdIncrementer())
            .start(asyncStep())
            .listener(new StopWatchJobListener())
            .build();
    }

    @Bean
    public Step defaultStep() throws Exception {
        return stepBuilderFactory.get("defaultStep")
            .<Customer, Customer2>chunk(100)
            .reader(pagingItemReader())
            .processor(defaultItemProcessor())
            .writer(defaultItemWriter())
            .build();

    }

    @Bean
    public Step asyncStep() throws Exception {

        return stepBuilderFactory.get("asyncStep")
            .<Customer, Customer2>chunk(100)
            .reader(pagingItemReader())
            .processor(asyncItemProcess())
            .writer(asyncItemWriter())
            .build();
    }

    @Bean
    public ItemStreamReader<Customer> pagingItemReader() {

        return new JpaPagingItemReaderBuilder<Customer>()
            .name("jpaPagingItemReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("select c from Customer c")
            .pageSize(100)
            .build();
    }
    @Bean
    public ItemProcessor<Customer, Customer2> defaultItemProcessor() throws InterruptedException {

        return item -> {
            Thread.sleep(30);
            return Customer2.builder()
                .id(item.getId())
                .name(item.getName().toUpperCase())
                .age(item.getAge() * 10000)
                .build();
        };
    }

    @Bean
    public ItemWriter<Customer2> defaultItemWriter() throws Exception {

        JpaItemWriter<Customer2> objectJpaItemWriter = new JpaItemWriter<>();
        objectJpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return objectJpaItemWriter::write;

    }

    @Bean
    public AsyncItemProcessor asyncItemProcess() throws InterruptedException {
        AsyncItemProcessor<Customer, Customer2> asyncItemProcessor = new AsyncItemProcessor<>();
        asyncItemProcessor.setDelegate(defaultItemProcessor());
        asyncItemProcessor.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return asyncItemProcessor;
    }

    @Bean
    public AsyncItemWriter<Customer2> asyncItemWriter() throws Exception {
        AsyncItemWriter<Customer2> asyncItemWriter = new AsyncItemWriter<>();
        asyncItemWriter.setDelegate(defaultItemWriter());
        return asyncItemWriter;
    }
}
