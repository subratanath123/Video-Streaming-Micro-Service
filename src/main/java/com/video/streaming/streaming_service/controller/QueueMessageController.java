package com.video.streaming.streaming_service.controller;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueueMessageController {

    @Value("${spring.cloud.aws.sqs.endpoint}")
    private String sqsEndpoint;

    @Autowired
    private SqsTemplate sqsTemplate;

    @RequestMapping("/v1/sqs/sendMessage")
    public void sendEvent(){
        sqsTemplate.send(sqsEndpoint, "test message");
    }

}
