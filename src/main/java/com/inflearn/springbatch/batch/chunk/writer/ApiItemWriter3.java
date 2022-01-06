package com.inflearn.springbatch.batch.chunk.writer;

import com.inflearn.springbatch.batch.domain.ApiRequestVO;
import com.inflearn.springbatch.service.APIService.ApiService;
import com.inflearn.springbatch.service.dto.ApiResponseVO;
import java.util.List;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;

public class ApiItemWriter3 extends FlatFileItemWriter<ApiRequestVO> {

    private final ApiService apiService;

    public ApiItemWriter3(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void write(List<? extends ApiRequestVO> items) throws Exception {
        ApiResponseVO responseVO = apiService.service(items);

        items.forEach(s-> s.setStatus(responseVO.getStatus()));

        super.setResource(new FileSystemResource("C:\\Users\\hoo\\IdeaProjects\\inflearn\\springbatch\\src\\main\\resources\\product3.txt"));
        super.open(new ExecutionContext());
        super.setLineAggregator(new DelimitedLineAggregator<>());
        super.setAppendAllowed(true); // 계속 추가 하겠다.
        super.write(items);
    }
}
