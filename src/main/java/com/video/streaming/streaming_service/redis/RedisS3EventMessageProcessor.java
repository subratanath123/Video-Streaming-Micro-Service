package com.video.streaming.streaming_service.redis;

import com.video.streaming.streaming_service.dto.S3Event;
import com.video.streaming.streaming_service.service.MediaConvertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;

import static com.video.streaming.streaming_service.constants.Constants.*;

@Service
public class RedisS3EventMessageProcessor implements StreamListener<String, ObjectRecord<String, S3Event>> {

    private Logger log = LoggerFactory.getLogger(RedisS3EventMessageProcessor.class);

    @Autowired
    private RedisTemplate<String, S3Event> redisBaseEntityRedisTemplate;

    @Autowired
    private MediaConvertService mediaConvertService;

    @Override
    public void onMessage(ObjectRecord<String, S3Event> record) {
        S3Event s3Event = record.getValue();

        mediaConvertService.createMediaConvertJob(
                s3Event.detail().object().key(),
                AWS_VIDEO_OUTPUT_BUCKET,
                AWS_VIDEO_JOB_TEMPLATE
        );

        log.info("Message is consuming for user event {}", s3Event.id());

        redisBaseEntityRedisTemplate
                .opsForStream()
                .acknowledge(REDIS_STREAM_SERVER_GROUP, record);
    }
}