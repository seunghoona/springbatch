package com.inflearn.springbatch.batch.job.file;

import com.inflearn.springbatch.batch.chunk.processor.FileItemProcessor;
import com.inflearn.springbatch.batch.domain.Product;
import com.inflearn.springbatch.batch.domain.ProductVO;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
public class FileJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    // 파일을 저장
    private final EntityManagerFactory emf;


    @Bean
    public Job fileJob() {
        return jobBuilderFactory.get("fileJob")
            .start(fileStep())
            .build();
    }

    @Bean
    public Step fileStep() {
        return stepBuilderFactory.get("fileStep")
            .<ProductVO, Product>chunk(10)
            .reader(fileItemReader(null))
            .processor(fileItemProcessor())
            .writer(fileItemWriter())
            .build();
    }
    @Bean
    @StepScope
    public FlatFileItemReader<ProductVO> fileItemReader (
        @Value("#{jobParameters['requestDate']}") String requestDate
    ) {
        return new FlatFileItemReaderBuilder<ProductVO>()
            .name("flatFile")
            .resource(new ClassPathResource("product_" + requestDate + ".csv"))
            .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
            // 어떤 클래스에게 맵핑할 것인가?
            .targetType(ProductVO.class)
            // 하나의 라인을 읽을 때 첫번째 라인은 스킵
            .linesToSkip(1)
            // 구분자 길이 구분자 기호가 있다.
            .delimited().delimiter(",")
            .names("name","price","type")
            .build();
    }

    @Bean
    public ItemProcessor<ProductVO, Product> fileItemProcessor() {
        return new FileItemProcessor();
    }

    @Bean
    public JpaItemWriter<Product> fileItemWriter() {
        return new JpaItemWriterBuilder<Product>()
            .entityManagerFactory(emf)
            .usePersist(true)
            .build();
    }

}
