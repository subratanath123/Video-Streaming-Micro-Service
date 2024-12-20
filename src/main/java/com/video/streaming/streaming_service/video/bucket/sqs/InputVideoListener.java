package com.video.streaming.streaming_service.video.bucket.sqs;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.model.Message;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class InputVideoListener {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @SqsListener("receive-video-upload-event-queue")
    public void listen(Message message) {

        Map<String, String> inputBucketUploadDataMap = new HashMap<>();
        inputBucketUploadDataMap.put("bucket-file-create-event", message.body());

        redisTemplate
                .opsForStream()
                .add("video-upload-bucket-event", inputBucketUploadDataMap);

        System.out.println("Message received on listen method at {}" + OffsetDateTime.now());
    }
}
