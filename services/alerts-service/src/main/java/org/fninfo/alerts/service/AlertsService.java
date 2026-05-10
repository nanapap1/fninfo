package org.fninfo.alerts.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.fninfo.alerts.runnable.CheckAlerts;
import org.fninfo.common.dto.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class AlertsService {
    private static final Logger log = LoggerFactory.getLogger(AlertsService.class);
    private final RestClient restClient;
    private final ObjectMapper mapper;

    public AlertsService(RestClient restClient, ObjectMapper mapper) {
        this.restClient = restClient;
        this.mapper = mapper;
    }

    public Map<String, List<Alert>> getAlerts(Boolean status){
        if(status) {
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
                    return result;
                }
            } catch (Exception e) {
                throw e;
            }
        };
        return null;
    }

    private boolean checkDate(String date) {
        ZoneId zone = ZoneId.of("Europe/Moscow");
        LocalDate targetDate = Instant.parse(date).atZone(zone).toLocalDate();
        return targetDate.isEqual(LocalDate.now(zone).plusDays(1));
    }

}
