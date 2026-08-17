package org.fninfo.alerts.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

@Configuration
public class WebConfig {

    @Bean
    public RestClient alerstConnect() {
        return RestClient.builder()
                .baseUrl("https://freethevbucks.com")
                .requestFactory(new JdkClientHttpRequestFactory())
                .messageConverters(con -> {
                    con.add(new FormHttpMessageConverter());
                    con.add(new StringHttpMessageConverter());
                    con.add(new MappingJackson2HttpMessageConverter());
                })
                .build();
    }
}
