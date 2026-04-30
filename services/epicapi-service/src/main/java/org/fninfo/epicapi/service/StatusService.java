package org.fninfo.epicapi.service;


import jakarta.annotation.PostConstruct;
import org.fninfo.common.dto.ChangeStatusEvent;
import org.fninfo.epicapi.dto.Status;
import org.fninfo.epicapi.runnable.CheckServerStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class StatusService {

    private final TaskScheduler taskScheduler;
    private final CheckServerStatus checkServerStatus;
    private final Status status;
    private final StreamBridge streamBridge;
    @Autowired
    public StatusService(Status status, CheckServerStatus checkServerStatus, TaskScheduler taskScheduler,StreamBridge streamBridge) {
        this.checkServerStatus = checkServerStatus;
        this.taskScheduler = taskScheduler;
        this.status = status;
        this.streamBridge = streamBridge;
    }
    @PostConstruct
    public void startTracking(){
        taskScheduler.scheduleWithFixedDelay(this::checking, Instant.now().plus(Duration.ofSeconds(20)), Duration.ofSeconds(20));
    }

    private void checking(){
        boolean stat = status.isUp();
        boolean flag = !status.getStatus().equalsIgnoreCase("neutral");
        checkServerStatus.run();
        if(stat != status.isUp() && flag) {
            streamBridge.send("statusChange-out-0", new ChangeStatusEvent(status.isUp()));
        }
    }
}
