package org.fninfo.tg.controller;

import org.fninfo.tg.dto.Request;
import org.fninfo.tg.dto.SubRequest;
import org.fninfo.tg.model.Game;
import org.fninfo.tg.service.ChatIdService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@RestController
public class ChatIdController {
    private final ChatIdService chatIdService;
    private final String authKey;

    public ChatIdController(ChatIdService chatIdService, @Value("${AUTH_IMAGE_KEY}") String authKey) {
        this.chatIdService = chatIdService;
        this.authKey = authKey;
    }

    @PostMapping("/main/change")
    public void changeMainId(@RequestBody Request request) {
        if(authKey.equals(request.key())) {
            if (!chatIdService.setMainId(request.name()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal format");
        }
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have permissions to make such response");
    }

    @PostMapping("/subscriptor/add")
    public void addSubscriptor(@RequestBody SubRequest request) {
        if(Arrays.stream(Game.values()).dropWhile(x -> !x.name().equalsIgnoreCase(request.subscription())).count() <=0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown subsription form");
        if(authKey.equals(request.key())) {
            if (!chatIdService.addListener(request))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal format");
        }
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have permissions to make such response");
    }

    @DeleteMapping("/subscriptor/delete")
    public void deleteSubscriptor(@RequestBody SubRequest request) {
        if(Arrays.stream(Game.values()).dropWhile(x -> !x.name().equalsIgnoreCase(request.subscription())).count() <=0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown subsription form");
        if(authKey.equals(request.key())) {
            if (!chatIdService.deleteListener(request))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal format");
        }
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have permissions to make such response");
    }


}
