package org.fninfo.epicapi.config;

import com.rabbitmq.client.impl.Environment;
import org.fninfo.epicapi.dto.Authenficator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestClient;


@Configuration
@EnableScheduling
public class AuthConfig {
    @Value("${client}")
    private String clientId;


    private final RestClient restClient;

    @Autowired
    public AuthConfig(RestClient restClient) {
        this.restClient = restClient;
    }


    @Bean(name="clientAccess")
    public Authenficator clientAuthenticate() {
        return restClient.post()
                .uri("https://account-public-service-prod.ol.epicgames.com/" +
                        "account/api/oauth/token")
                .contentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .header("Authorization", this.clientId)
                .body("grant_type=client_credentials")
                .retrieve()
                .body(Authenficator.class);
    }

}
