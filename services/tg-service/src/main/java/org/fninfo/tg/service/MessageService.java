package org.fninfo.tg.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fninfo.common.dto.TelegramEvent;
import org.fninfo.tg.dto.Answer;
import org.fninfo.tg.exception.MessageNotSentException;
import org.fninfo.tg.model.Game;
import org.fninfo.tg.repo.TGRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Set;

@Service
public class MessageService {

    private static final Logger log = LogManager.getLogger(MessageService.class);
    private final RestClient restClient;
    private final TGRepository tgRepository;

    public MessageService(TGRepository tgRepository, RestClient restClient) {
        this.tgRepository = tgRepository;
        this.restClient = restClient;
    }

    public void sendMessage(TelegramEvent event, Game game,String token) {
        Set<String> id = tgRepository.getIDs(game.name());
        for(String one : id) {
           if (event.image() != null) {
                        try {
                            Map<String, String> data = Map.of("chat_id", one,  "photo", event.image(), "caption", event.text());
                            this.sendTextAndImageMessage(data, token);
                        } catch (Exception e) {
                            log.warn("Failed to send to {}: {}", one, e.getMessage());
                        }
           }
           else {
               try {
                   Map<String, String> data = Map.of("chat_id", one, "text", event.text());
                   this.sendTextMessage(data, token);
               } catch (Exception e) {
                   log.warn("Failed to send to {}: {}", one, e.getMessage());
               }
           }
       }

    }

    private void sendTextMessage(Map<String,String> data, String token) {
        Answer ans = restClient.post()
                .uri("/bot{token}/sendMessage", token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .body(Answer.class);
        if(!ans.isOk())
            throw new MessageNotSentException("Impossible to send a message");
    }

    private void sendTextAndImageMessage(Map<String,String> data, String token) {
        Answer ans = restClient.post()
                    .uri("/bot{token}/sendPhoto", token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(data)
                    .retrieve()
                    .body(Answer.class);
        if(!ans.isOk())
            throw new MessageNotSentException("Impossible to send a message");
    }

}
