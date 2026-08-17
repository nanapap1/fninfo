package org.fninfo.epicapi.service;

import jakarta.annotation.PostConstruct;
import org.fninfo.epicapi.runnable.AlertStatus;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class AlertService {
    private final TaskScheduler taskScheduler;
    private final AlertStatus alertStatus;

    public AlertService(TaskScheduler taskScheduler, AlertStatus alertStatus) {
        this.taskScheduler = taskScheduler;
        this.alertStatus = alertStatus;
    }

    @PostConstruct
    public void setUpdateClientToken() {
        taskScheduler.scheduleWithFixedDelay(alertStatus, Instant.now().plus(Duration.ofSeconds(10)),Duration.ofSeconds(10));
    }
}
