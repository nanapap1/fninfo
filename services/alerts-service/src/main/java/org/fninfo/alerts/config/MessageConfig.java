package org.fninfo.alerts.config;

import org.fninfo.alerts.service.AlertsService;
import org.fninfo.common.dto.Alert;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class MessageConfig {
    private final AlertsService alertsService;

    public MessageConfig(AlertsService alertsService) {
        this.alertsService = alertsService;
    }

    @Bean
    public Function<Boolean, Map<String, List<Alert>>> alertFunction() {
        return alertsService::getAlerts;
    }
}
