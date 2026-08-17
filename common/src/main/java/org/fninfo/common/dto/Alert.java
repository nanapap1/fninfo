package org.fninfo.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Alert {
    private String zone;
    @JsonProperty("name")
    private String name;
    @JsonProperty("rewards")
    private String reward;
    @JsonProperty("power_level")
    private int powerLevel;

    public static final String[] ALERT_TYPES = {
            "MissionAlert_MiniBoss",
            "MissionAlert_Storm",
            "MissionAlert_Megalert_4p",
            "MissionAlert_ElementalZone_Fire",
            "MissionAlert_ElementalZone_Water",
            "MissionAlert_ElementalZone_Nature",
            "MissionAlert_Dudebro",
            "MissionAlert_Phoenix_PassiveMiniboss",
            "MissionAlert_Megalert_Phoenix",
            "MissionAlert_Phoenix_ActiveNature",
            "MissionAlert_Phoenix_PassiveFire",
            "MissionAlert_Phoenix_PassiveWater",
            "MissionAlert_Megalert_Phoenix_Lightning"
    };

    public Alert() {

    }

    public Alert(String zone, String name, String reward, int powerLevel) {
        this.zone = zone;
        this.name = name;
        this.reward = reward;
        this.powerLevel = powerLevel;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(int powerLevel) {
        this.powerLevel = powerLevel;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "zone='" + zone + '\'' +
                ", name='" + name + '\'' +
                ", reward='" + reward + '\'' +
                ", powerLevel=" + powerLevel +
                '}';
    }
}
