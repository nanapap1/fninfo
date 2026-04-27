package org.fninfo.tg.service;

import org.fninfo.tg.repo.TGRepository;
import org.springframework.beans.factory.annotation.Value;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageService {
    private final TGRepository tgRepository;
    private final MessageService messageService;
    private final String infoToken;

    public ImageService(TGRepository tgRepository, MessageService messageService, @Value("${TG_TOKEN_INFO}") String infoToken) {
        this.tgRepository = tgRepository;
        this.messageService = messageService;
        this.infoToken = infoToken;
    }

    public boolean addImage(String name) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("images/"+ name+ ".png");
        String get = messageService.sendPhotoAsHeader(stream,name + ".png",tgRepository.getID("test:send"),infoToken);
        return tgRepository.addPhoto(name,get);
    }

}
