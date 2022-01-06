package com.inflearn.springbatch.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiRequestVO {

    private Long id;
    private ProductVO productVO;
    private String status;

}
