package com.inflearn.springbatch.service.dto;

import com.inflearn.springbatch.batch.domain.ApiRequestVO;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiInfo {

    private String url;
    private List<? extends ApiRequestVO> apiRequestVOs;
}
