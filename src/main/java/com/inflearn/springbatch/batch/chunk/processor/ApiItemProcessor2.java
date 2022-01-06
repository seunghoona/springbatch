package com.inflearn.springbatch.batch.chunk.processor;


import com.inflearn.springbatch.batch.domain.ApiRequestVO;
import com.inflearn.springbatch.batch.domain.ProductVO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ApiItemProcessor2 implements ItemProcessor<ProductVO, ApiRequestVO> {

    @Override
    public ApiRequestVO process(ProductVO item) throws Exception {
        return ApiRequestVO.builder()
            .id(item.getId())
            .productVO(item)
            .build();
    }
}
