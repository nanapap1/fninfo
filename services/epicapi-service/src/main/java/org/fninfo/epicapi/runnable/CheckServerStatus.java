package org.fninfo.epicapi.runnable;

import org.fninfo.epicapi.dto.Authenficator;
import org.fninfo.epicapi.dto.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CheckServerStatus extends TemplateRunner implements Runnable{

    private final Status status;

    @Autowired
    public CheckServerStatus(@Qualifier("clientAccess")  Authenficator authenficator, RestClient restClient, Status status) {
        super(authenficator,restClient);
        this.status = status;
    }

    @Override
    public void run() {
        try {
            status.setUp(this.restClient.get()
                    .uri("https://lightswitch-public-service-prod.ol.epicgames.com/lightswitch/api/service/fortnite/status")
                    .header("Authorization", String.format("Bearer %s",authenficator.getAccessToken()))
                    .retrieve()
                    .body(Status.class).getStatus());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
