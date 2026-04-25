package org.fninfo.epicapi.runnable;

import org.fninfo.epicapi.dto.Authenficator;
import org.springframework.web.client.RestClient;

public abstract class TemplateRunner {
    protected RestClient restClient;
    protected Authenficator authenficator;

    public TemplateRunner(Authenficator authenficator, RestClient restClient) {
        this.authenficator = authenficator;
        this.restClient = restClient;
    }
}
