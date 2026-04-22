package org.fninfo.epicapi.service;


import jakarta.annotation.PostConstruct;
import org.fninfo.epicapi.dto.Authenficator;
import org.fninfo.epicapi.runnable.UpdateClientToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {

    private final UpdateClientToken updateClientToken;
    private final TaskScheduler taskScheduler;
    private final Authenficator authenficator;

    @Autowired
    public TokenService(@Qualifier("clientAccess") Authenficator authenficator, UpdateClientToken updateClientToken, TaskScheduler taskScheduler) {
        this.authenficator = authenficator;
        this.updateClientToken = updateClientToken;
        this.taskScheduler = taskScheduler;
    }

    @PostConstruct
    public void setUpdateClientToken() {
        taskScheduler.scheduleWithFixedDelay(updateClientToken, Instant.now().plus(Duration.ofSeconds(authenficator.getEndAt() - 10)),Duration.ofSeconds(authenficator.getEndAt() - 10));
    }

}
