package org.fninfo.epicapi.runnable;

import com.fasterxml.jackson.databind.JsonNode;
import org.fninfo.epicapi.dto.Authenficator;
import org.fninfo.epicapi.dto.Status;
import org.fninfo.epicapi.repo.AlertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component

public class AlertStatus extends TemplateRunner implements Runnable{
    private static final Logger log = LoggerFactory.getLogger(AlertStatus.class);
    private final AlertRepository alertRepository;
    private final StreamBridge streamBridge;

    public AlertStatus(@Qualifier("clientAccess") Authenficator authenficator, RestClient restClient, AlertRepository alertRepository, StreamBridge streamBridge) {
        super(authenficator, restClient);
        this.alertRepository = alertRepository;
        this.streamBridge = streamBridge;
    }

    @Override
    public void run() {
        JsonNode node = this.restClient.get()
                .uri("https://fngw-mcp-gc-livefn.ol.epicgames.com/fortnite/api/game/v2/world/info")
                .header("Authorization", String.format("Bearer %s",authenficator.getAccessToken()))
                .retrieve()
                .body(JsonNode.class).path("missionAlerts");
        boolean send = false;

        for (JsonNode check : node) {
            Set<String> alerts = new HashSet<>();
            for (JsonNode jsonNode : check.path("availableMissionAlerts")) {
                alerts.add(jsonNode.path("missionAlertGuid").textValue());
            }
            if(!send && !alertRepository.compare(check.path("theaterId").textValue(),alerts)) {
                alertRepository.addAlerts(check.path("theaterId").textValue(),alerts);
                streamBridge.send("alertsChange-out-0", true);
                send = true;
            }
            if(send) {
                alertRepository.addAlerts(check.path("theaterId").textValue(),alerts);
            }
        }
    }
}
