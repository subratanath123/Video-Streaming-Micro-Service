package com.video.streaming.streaming_service.video.bucket.sqs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.video.streaming.streaming_service.dto.S3Event;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.model.Message;

import java.time.OffsetDateTime;
import java.util.HashMap;

import static com.video.streaming.streaming_service.constants.Constants.BUCKET_CREATE_EVENT_STREAM;
import static com.video.streaming.streaming_service.constants.Constants.PAYLOAD_KEY;

@Service
public class InputVideoListener {

    private final Logger log = LoggerFactory.getLogger(InputVideoListener.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @SqsListener("receive-video-upload-event-queue")
    public void listen(Message message) throws JsonProcessingException {
        String sqsEventBody = message.body();
        ObjectMapper objectMapper = new ObjectMapper();

        String decodedJson = objectMapper.convertValue(sqsEventBody, String.class);
        S3Event s3Event = objectMapper.readValue(decodedJson, S3Event.class);

        ObjectRecord<String, S3Event> record = StreamRecords
                .newRecord()
                .ofObject(s3Event)
                .withStreamKey(BUCKET_CREATE_EVENT_STREAM);

        RecordId recordId = this.redisTemplate.opsForStream().add(record);

        log.info("Message received on listen method at {}, stream recordId {}", OffsetDateTime.now(), recordId);
    }
}
