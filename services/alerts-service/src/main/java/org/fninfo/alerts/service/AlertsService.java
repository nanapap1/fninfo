package org.fninfo.alerts.service;


import jakarta.annotation.PostConstruct;
import org.fninfo.alerts.runnable.CheckAlerts;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.TimeZone;

@Service
public class AlertsService {
    private final TaskScheduler taskScheduler;
    private final CheckAlerts checkAlerts;

    public AlertsService(TaskScheduler taskScheduler, CheckAlerts checkAlerts) {
        this.taskScheduler = taskScheduler;
        this.checkAlerts = checkAlerts;
    }

    @PostConstruct
    public void startTracking(){
        taskScheduler.schedule(checkAlerts, new CronTrigger("30 20 3 * * ?", ZoneId.of("Europe/Moscow")));
    }
}
