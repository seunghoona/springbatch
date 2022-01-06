package com.inflearn.springbatch.batch.chunk.processor;

import com.inflearn.springbatch.batch.domain.Product;
import com.inflearn.springbatch.batch.domain.ProductVO;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

public class FileItemProcessor implements ItemProcessor<ProductVO, Product> {

    @Override
    public Product process(ProductVO item) {
        ModelMapper modelMapper = new ModelMapper();

        Product map = modelMapper.map(item, Product.class);

        return map;
    }
}
