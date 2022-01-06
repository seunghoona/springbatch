package com.inflearn.springbatch.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponseVO {

    private String status;
    private String message;
}
