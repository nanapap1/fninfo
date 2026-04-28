package org.fninfo.tg.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fninfo.common.dto.TelegramEvent;
import org.fninfo.tg.dto.Answer;
import org.fninfo.tg.exception.MessageNotSendException;
import org.fninfo.tg.model.Game;
import org.fninfo.tg.repo.TGRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.io.InputStream;
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
        Set<String> id = tgRepository.getIDs("send:"+ game.name());
        for(String one : id) {
           if (event.image() != null) {
                        try {
                            Map<String, String> data = Map.of("chat_id", one,  "photo", tgRepository.getPhoto("photo:"+event.image()), "caption", event.text());
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
            throw new MessageNotSendException("Impossible to send a message");
    }

    private void sendTextAndImageMessage(Map<String, String> data, String token) {
        Answer ans = restClient.post()
                    .uri("/bot{token}/sendPhoto", token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(data)
                    .retrieve()
                    .body(Answer.class);
        if(!ans.isOk())
            throw new MessageNotSendException("Impossible to send a message");
    }

    public String sendPhotoAsHeader(InputStream stream, String filename, String id, String token) {
        try {
            byte[] imageBytes = stream.readAllBytes();
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("chat_id", id);
            builder.part("photo",  new ByteArrayResource(imageBytes) {
                @Override
                public String getFilename() { return filename; }
            });
            MultiValueMap<String, HttpEntity<?>> parts = builder.build();
            Answer ans = restClient.post()
                    .uri("/bot{token}/sendPhoto", token)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(parts)
                    .retrieve()
                    .body(Answer.class);
            if(!ans.isOk())
                throw new MessageNotSendException("Impossible to send a message");
            return ans.getFileId();
        }
        catch (Exception e) {
            log.warn("Failed to send to bot {}: {}", id, e.getMessage());
            return "";
        }
    }

}
