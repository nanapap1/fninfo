package org.fninfo.tg.controller;

import org.fninfo.tg.dto.ImageRequest;
import org.fninfo.tg.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

public class ImageController {
    private final ImageService imageService;
    private final String authKey;


    public ImageController(ImageService imageService, @Value("${AUTH_IMAGE_KEY}") String authKey) {
        this.imageService = imageService;
        this.authKey = authKey;
    }

    @PostMapping("addImage")
    public void addInfo(@RequestBody ImageRequest info) {
        if(authKey.equals(info.key())) {
            if (!imageService.addImage(info.name()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal format");
        }
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You don't have permissions to make such response");
    }
}
