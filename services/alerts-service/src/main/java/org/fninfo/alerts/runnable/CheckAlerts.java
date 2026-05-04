package org.fninfo.alerts.runnable;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fninfo.common.dto.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.*;
import java.util.*;

@Component
public class CheckAlerts implements Runnable{

    private static final Logger log = LoggerFactory.getLogger(CheckAlerts.class);
    private final RestClient restClient;
    private final ObjectMapper mapper;
    private final StreamBridge streamBridge;

    public CheckAlerts(RestClient restClient, ObjectMapper mapper, StreamBridge streamBridge) {
        this.restClient = restClient;
        this.mapper = mapper;
        this.streamBridge = streamBridge;
    }

    @Override
    public void run() {
        try {
           JsonNode maps = this.restClient.get()
                    .uri("/apiformissiontracking.json")
                    .retrieve()
                    .body(JsonNode.class);
           if(checkDate(maps.get("alerts").get("Stonewood").get("MissionAlert_MiniBoss").get(0).get("expires_at").asText())) {
               JsonNode alertsNode = maps.path("alerts");
               Map<String, List<Alert>> result = new LinkedHashMap<>();
               Iterator<String> it = alertsNode.fieldNames();
               while(it.hasNext()) {
                   String category = it.next();
                   JsonNode group = alertsNode.get(category);
                   List<JsonNode> mergedItems = new ArrayList<>();

                   for (String alertType : Alert.ALERT_TYPES) {
                       JsonNode alerts = group.path(alertType);
                       if (alerts.isArray()) {
                           alerts.forEach(mergedItems::add);
                       }
                   }

                   List<Alert> dtos = new ArrayList<>();
                   for (JsonNode item : mergedItems) {
                       try {
                           Alert dto = mapper.treeToValue(item, Alert.class);
                           dto.setZone(category);
                           dtos.add(dto);
                       } catch (Exception exec) {
                           log.warn("A problem happened during parsing the string: " + dtos.toString());
                       }
                   }
                   result.put(category, dtos);
               }
               System.out.println("senenenenene");
               streamBridge.send("alertsSTW-out-0", result);
           }
        } catch (Exception e) {
           throw e;
        }
    }

    private boolean checkDate(String date) {
        ZoneId zone = ZoneId.of("Europe/Moscow");
        LocalDate targetDate = Instant.parse(date).atZone(zone).toLocalDate();
        return targetDate.isEqual(LocalDate.now(zone).plusDays(1));
    }

}

