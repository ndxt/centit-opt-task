package com.centit.task.client.service.impl;

import com.centit.framework.appclient.AppSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserTaskAppSession extends AppSession {
    @Value("${userTask.server.url:}")
    private String userTaskUrl;


    @PostConstruct
    public void init() {
        super.setAppServerUrl(userTaskUrl);
    }
}
