package com.inflearn.springbatch.service.APIService;

import com.inflearn.springbatch.batch.domain.ApiRequestVO;
import com.inflearn.springbatch.service.dto.ApiInfo;
import com.inflearn.springbatch.service.dto.ApiResponseVO;
import java.io.IOException;
import java.util.List;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public abstract class ApiService {

    public ApiResponseVO service(List<? extends ApiRequestVO> apiRequestVO) {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

        RestTemplate restTemplate = restTemplateBuilder
            .errorHandler(new ResponseErrorHandler() {

                @Override
                public boolean hasError(ClientHttpResponse response) throws IOException {
                    return false;
                }

                @Override
                public void handleError(ClientHttpResponse response) throws IOException {

                }
            })
            .build();

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        ApiInfo apiInfo = ApiInfo.builder()
            .apiRequestVOs(apiRequestVO).build();

        return doApiService(restTemplate, apiInfo);
    }

    protected abstract ApiResponseVO doApiService(RestTemplate restTemplate, ApiInfo apiInfo);
}
