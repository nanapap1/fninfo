package org.fninfo.info.config;

import org.fninfo.common.dto.ChangeStatusEvent;
import org.fninfo.common.dto.TelegramEvent;
import org.fninfo.info.service.AnalyzeStatusService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
public class MessageConfig {
    private final AnalyzeStatusService analyzeStatusService;

    public MessageConfig(AnalyzeStatusService analyzeStatusService) {
        this.analyzeStatusService = analyzeStatusService;
    }

    @Bean
    public Function<ChangeStatusEvent, TelegramEvent> receiveChangeStatus() {
        return (ChangeStatusEvent event) -> {
            return AnalyzeStatusService.analyze(event);
        };
    }

}
