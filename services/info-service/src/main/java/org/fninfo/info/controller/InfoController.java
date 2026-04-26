package org.fninfo.info.controller;


import org.apache.http.HttpException;
import org.fninfo.info.dto.InfoRequest;
import org.fninfo.info.entity.Info;
import org.fninfo.info.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class InfoController {
    private final InfoService infoService;

    @Autowired
    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @PostMapping("addInfo")
    public void addInfo(@RequestBody InfoRequest info) {
        if(!infoService.addInfo(info))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal format");
    }
}
