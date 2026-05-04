package org.fninfo.epicapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class Status {
    @JsonProperty("status")
    private volatile String isUp = "neutral";

    public Status() {
    }

    public boolean isUp() {
        return isUp.equalsIgnoreCase("up");
    }

    public String getStatus() {
        return isUp;
    }

    public void setUp(String up) {
        isUp = up;
    }
}
