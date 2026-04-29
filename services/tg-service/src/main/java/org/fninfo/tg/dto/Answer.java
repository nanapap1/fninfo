package org.fninfo.tg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Answer {
    @JsonProperty("ok")
    private boolean isOk;

    @JsonProperty("result")
    private JsonNode result;

    public Answer() {
    }

    public Answer(boolean isOk, JsonNode result) {
        this.isOk = isOk;
        this.result = result;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public JsonNode getResult() {
        return result;
    }

    public void setResult(JsonNode result) {
        this.result = result;
    }

    public String getFileId() {
        JsonNode next = result.get("photo");
        JsonNode finall = next.get(next.size()-1);
        return finall.get("file_id").asText();
    }

}
