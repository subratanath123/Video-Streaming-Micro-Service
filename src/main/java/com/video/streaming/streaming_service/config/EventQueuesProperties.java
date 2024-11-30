package com.video.streaming.streaming_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "events.queues")
public class EventQueuesProperties {

    private String receiveVideoUploadEventQueue;

    public String getReceiveVideoUploadEventQueue() {
        return receiveVideoUploadEventQueue;
    }

    public void setReceiveVideoUploadEventQueue(String receiveVideoUploadEventQueue) {
        this.receiveVideoUploadEventQueue = receiveVideoUploadEventQueue;
    }
}