package org.fninfo.tg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                .baseUrl("https://api.telegram.org")
                .requestFactory(new JdkClientHttpRequestFactory())
                .messageConverters(con -> {
                    con.add(new FormHttpMessageConverter());
                    con.add(new StringHttpMessageConverter());
                    con.add(new MappingJackson2HttpMessageConverter());
                })
                .build();
    }
}
