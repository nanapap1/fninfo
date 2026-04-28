package org.fninfo.tg.config;

import org.fninfo.common.dto.TelegramEvent;
import org.fninfo.tg.model.Game;
import org.fninfo.tg.service.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class MessageConfig {
    private final MessageService messageService;
    private final String infoToken;

    public MessageConfig(MessageService messageService, @Value("${TG_TOKEN_INFO}") String infoToken) {
        this.messageService = messageService;
        this.infoToken = infoToken;
    }

    @Bean
    public Consumer<TelegramEvent> sendMessageFortnite() {
        return (event) -> {this.messageService.sendMessage(event, Game.FORTNITE,this.infoToken);};
    }
}
