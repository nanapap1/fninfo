package org.fninfo.epicapi.config;

import org.fninfo.epicapi.exception.AuthException;
import org.fninfo.epicapi.exception.ServerException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .requestFactory(new JdkClientHttpRequestFactory())
                .messageConverters(con -> {
                    con.add(new FormHttpMessageConverter());
                    con.add(new StringHttpMessageConverter());
                    con.add(new MappingJackson2HttpMessageConverter());
                })
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    if(response.getStatusCode().is4xxClientError()) {
                        if (response.getStatusCode().value() == 400)
                            throw new AuthException("Please check the credentials");
                        if (response.getStatusCode().value() == 401)
                            throw new AuthException("Your token probably has expired");
                    }
                    if(response.getStatusCode().is5xxServerError())
                        throw new ServerException("Epic Games API is currently unavailable");
                })
                .build();
    }

}
