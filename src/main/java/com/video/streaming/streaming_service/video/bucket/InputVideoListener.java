package com.video.streaming.streaming_service.video.bucket;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.model.Message;

import java.time.OffsetDateTime;

@Service
public class InputVideoListener {

    @SqsListener("receive-video-upload-event-queue")
    public void listen(Message message) {

        System.out.println("Message received on listen method at {}" + OffsetDateTime.now());
    }
}
