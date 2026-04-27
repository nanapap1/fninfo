package org.fninfo.tg.service;

import org.fninfo.tg.dto.SubRequest;
import org.fninfo.tg.model.Game;
import org.fninfo.tg.repo.TGRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatIdService {
    private final TGRepository tgRepository;

    public ChatIdService(TGRepository tgRepository) {
        this.tgRepository = tgRepository;
    }

    public boolean setMainId(String chatId) {
        return tgRepository.addElement("test:send", chatId);
    }

    public boolean addListener(SubRequest subRequest) {
        return tgRepository.addElementSet("send:" + subRequest.subscription(),subRequest.chatId());
    }

    public boolean deleteListener(SubRequest subRequest) {
        return tgRepository.deleteElementSet("send:" + subRequest.subscription(),subRequest.chatId());
    }
}
