package org.fninfo.info.service;

import org.fninfo.info.dto.InfoRequest;
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

    public boolean addInfo(InfoRequest infoRequest){
        try {
        Info info = new Info();
        info.setImage(infoRequest.image());
        info.setText(infoRequest.text());
        info.setNameEvent(infoRequest.name());
        if(infoRequest.status())
            info.setStatusOf(true);
        infoRepository.save(info);
        return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean deleteInfo(InfoRequest infoRequest) {
        try{
            Info info = infoRepository.findByNameEventAndStatusOf(infoRequest.name(),infoRequest.status());
            if (info != null) {
                infoRepository.delete(info);
                return true;
            }
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }
}
