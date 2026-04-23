package org.fninfo.epicapi;

import org.fninfo.epicapi.dto.Authenficator;
import org.fninfo.epicapi.dto.Status;
import org.fninfo.epicapi.exceptions.AuthException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StatusCheckTest {
    private final RestClient restClient;
    private final Authenficator authenficator;

    @Autowired
    public StatusCheckTest(RestClient restClient, @Qualifier("clientAccess") Authenficator authenficator) {
        this.restClient = restClient;
        this.authenficator = authenficator;
    }

    @Test
    public void isSuccess() {
        HttpStatusCode code = this.restClient
                .get()
                .uri("https://lightswitch-public-service-prod.ol.epicgames.com/lightswitch/api/service/fortnite/status")
                .header("Authorization", String.format("Bearer %s",authenficator.getAccessToken()))
                .retrieve()
                .toBodilessEntity().getStatusCode();
        assertTrue(code.is2xxSuccessful());
    }

    @Test
    public void noAuthorization() {
        assertThrows(AuthException.class,
                () -> this.restClient
                        .get()
                        .uri("https://lightswitch-public-service-prod.ol.epicgames.com/lightswitch/api/service/fortnite/status")
                        .header("Authorization","Bearer 10asd12df34js3jf4")
                        .retrieve()
                        .body(String.class)
                );
    }

    @Test
    public void hasStatus() {
        assertNotNull(this.restClient
                .get()
                .uri("https://lightswitch-public-service-prod.ol.epicgames.com/lightswitch/api/service/fortnite/status")
                .header("Authorization", String.format("Bearer %s",authenficator.getAccessToken()))
                .retrieve()
                .body(Status.class).isUp());
    }
}
