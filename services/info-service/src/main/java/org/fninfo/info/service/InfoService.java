package org.fninfo.info.service;

import org.fninfo.info.entity.Info;
import org.fninfo.info.repo.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoService {
    private final InfoRepository infoRepository;

    @Autowired
    public InfoService(InfoRepository infoRepository) {
        this.infoRepository = infoRepository;
    }

    public boolean addInfo(Info info){
        try{
            infoRepository.save(info);
            return true;
        }
        catch (Exception e) {
            return false;
        }

    }
}
