package org.fninfo.epicapi.service;


import jakarta.annotation.PostConstruct;
import org.fninfo.epicapi.dto.Authenficator;
import org.fninfo.epicapi.dto.Status;
import org.fninfo.epicapi.runnable.CheckServerStatus;
import org.fninfo.epicapi.runnable.UpdateClientToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class StatusService {

    private final TaskScheduler taskScheduler;
    private final CheckServerStatus checkServerStatus;
    private final Status status;
    @Autowired
    public StatusService(Status status, CheckServerStatus checkServerStatus, TaskScheduler taskScheduler) {
        this.checkServerStatus = checkServerStatus;
        this.taskScheduler = taskScheduler;
        this.status = status;
    }
    @PostConstruct
    public void startTracking(){
        taskScheduler.scheduleWithFixedDelay(this::checking, Duration.ofSeconds(20));
    }

    private void checking(){
        boolean stat = status.isUp();
        checkServerStatus.run();
        if(stat != status.isUp()) {
            System.out.println("CHANGED");
        }
    }
}
