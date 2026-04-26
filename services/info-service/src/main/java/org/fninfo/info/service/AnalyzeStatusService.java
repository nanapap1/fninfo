package org.fninfo.info.service;

import org.fninfo.common.dto.ChangeStatusEvent;
import org.fninfo.common.dto.TelegramEvent;
import org.fninfo.info.entity.Info;
import org.fninfo.info.exception.NoSuchInformation;
import org.fninfo.info.repo.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyzeStatusService {

    private final InfoRepository infoRepository;

    @Autowired
    public AnalyzeStatusService(InfoRepository infoRepository) {
        this.infoRepository = infoRepository;
    }

    public TelegramEvent analyzeStatus(ChangeStatusEvent event) {
        try {
            Info info = infoRepository.findByNameEventAndStatusOf(event.getClass().getSimpleName(), event.isOnline());
            return new TelegramEvent(info.getText(), info.getImage());
        }
        catch (Exception e) {
            throw new NoSuchInformation("These message can't be analyze by InfoService");
        }

    }



}
