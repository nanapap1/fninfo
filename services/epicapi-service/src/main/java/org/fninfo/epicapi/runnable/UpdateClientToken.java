package org.fninfo.epicapi.runnable;

import org.fninfo.epicapi.dto.Authenficator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component

public class UpdateClientToken extends TemplateRunner implements Runnable {

    @Value("${client.id}")
    private String clientId;



    @Autowired
    public UpdateClientToken(@Qualifier("clientAccess")  Authenficator authenficator, RestClient restClient) {
        super(authenficator,restClient);
    }

    @Override
    public void run() {
        try {
            Authenficator auth =this.restClient.post()
                    .uri("https://account-public-service-prod.ol.epicgames.com/" +
                            "account/api/oauth/token")
                    .contentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                    .header("Authorization", this.clientId)
                    .body("grant_type=client_credentials")
                    .retrieve()
                    .body(Authenficator.class);
            authenficator.setAccessToken(auth.getAccessToken());
            authenficator.setEndAt(auth.getEndAt());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
