package com.inflearn.springbatch.service.APIService;

import com.inflearn.springbatch.service.dto.ApiInfo;
import com.inflearn.springbatch.service.dto.ApiResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService1 extends  ApiService{

    @Override
    protected ApiResponseVO doApiService(RestTemplate restTemplate, ApiInfo apiInfo) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
            "http://localhost:8084/api/product/1", apiInfo, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return ApiResponseVO.builder()
                .status(responseEntity.getStatusCode().toString())
                .message(responseEntity.getBody())
                .build();
        }

        return null;
    }
}
