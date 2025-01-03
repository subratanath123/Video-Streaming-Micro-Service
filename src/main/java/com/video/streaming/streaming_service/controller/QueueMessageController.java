package com.video.streaming.streaming_service.controller;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueueMessageController {

    @Value("${spring.cloud.aws.sqs.endpoint}")
    private String sqsEndpoint;

    @Autowired
    private SqsTemplate sqsTemplate;

    @GetMapping("/v1/sqs/sendMessage")
    public void sendEvent(){
        sqsTemplate.send(sqsEndpoint, "\n" +
                "{\n" +
                "  \"version\": \"0\",\n" +
                "  \"id\": \"f3165f4c-7bb3-8fca-d596-527a30c55ea2\",\n" +
                "  \"detail-type\": \"Object Created\",\n" +
                "  \"source\": \"aws.s3\",\n" +
                "  \"account\": \"242201311595\",\n" +
                "  \"time\": \"2024-12-20T06:20:58Z\",\n" +
                "  \"region\": \"us-east-1\",\n" +
                "  \"resources\": [\n" +
                "    \"arn:aws:s3:::video-input-bucket-final\"\n" +
                "  ],\n" +
                "  \"detail\": {\n" +
                "    \"version\": \"0\",\n" +
                "    \"bucket\": {\n" +
                "      \"name\": \"video-input-bucket-final\"\n" +
                "    },\n" +
                "    \"object\": {\n" +
                "      \"key\": \"shuvra.dev9@gmail.com/my-video.mp4\",\n" +
                "      \"size\": 154142,\n" +
                "      \"etag\": \"e23f8b7bba0f7da77a99122c436782d7\",\n" +
                "      \"sequencer\": \"0067650CCAB3218460\"\n" +
                "    },\n" +
                "    \"request-id\": \"YFR1TK81NDNQ8T6H\",\n" +
                "    \"requester\": \"242201311595\",\n" +
                "    \"source-ip-address\": \"103.113.225.201\",\n" +
                "    \"reason\": \"PutObject\"\n" +
                "  }\n" +
                "}\n" +
                "\n");
    }

}
