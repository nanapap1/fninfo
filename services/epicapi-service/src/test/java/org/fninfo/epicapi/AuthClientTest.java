package org.fninfo.epicapi;

import org.fninfo.epicapi.dto.Authenficator;
import org.fninfo.epicapi.exception.AuthException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthClientTest {


    private final String clientId;
    private final RestClient restClient;
    private final Authenficator authenficator;

    @Autowired
    public AuthClientTest(@Value("${client}") String id,RestClient restClient, @Qualifier("clientAccess") Authenficator authenficator) {
        this.clientId = id;
        this.restClient = restClient;
        this.authenficator = authenficator;
    }

    @Test
    public void isSuccesful() {
        HttpStatusCode code = this.restClient.post()
                .uri("https://account-public-service-prod.ol.epicgames.com/" +
                        "account/api/oauth/token")
                .contentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .header("Authorization", this.clientId)
                .body("grant_type=client_credentials")
                .retrieve().toBodilessEntity().getStatusCode();
        assertTrue(code.is2xxSuccessful());
    }

    @Test
    public void AuthenficationProblems() {
        assertThrows(AuthException.class, () -> this.restClient.post()
                .uri("https://account-public-service-prod.ol.epicgames.com/" +
                        "account/api/oauth/token")
                .contentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .header("Authorization", "asd038asdi2198cbrwqw3123")
                .body("grant_type=client_credentials")
                .retrieve()
                .body(String.class));
    }

    @Test
    public void hasToken() {
       assertNotNull(this.restClient.post()
               .uri("https://account-public-service-prod.ol.epicgames.com/" +
                       "account/api/oauth/token")
               .contentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
               .header("Authorization", this.clientId)
               .body("grant_type=client_credentials")
               .retrieve()
               .body(Authenficator.class).getAccessToken());
    }

}
