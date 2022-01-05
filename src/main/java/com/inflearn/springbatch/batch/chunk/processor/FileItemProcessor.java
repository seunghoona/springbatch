package com.inflearn.springbatch.batch.chunk.processor;

import com.inflearn.springbatch.batch.domain.Prodcut;
import com.inflearn.springbatch.batch.dto.ProductVO;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

public class FileItemProcessor implements ItemProcessor<ProductVO, Prodcut> {

    @Override
    public Prodcut process(ProductVO item) throws Exception {
        ModelMapper modelMapper = new ModelMapper();

        Prodcut map = modelMapper.map(item, Prodcut.class);

        return map;
    }
}
