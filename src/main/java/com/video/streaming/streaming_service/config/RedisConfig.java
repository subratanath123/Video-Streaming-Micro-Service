package com.video.streaming.streaming_service.config;

import com.video.streaming.streaming_service.dto.S3Event;
import com.video.streaming.streaming_service.redis.RedisConsumerGroupService;
import com.video.streaming.streaming_service.redis.RedisS3EventMessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;

import static com.video.streaming.streaming_service.constants.Constants.BUCKET_CREATE_EVENT_STREAM;
import static com.video.streaming.streaming_service.constants.Constants.REDIS_STREAM_SERVER_GROUP;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.username}")
    private String redisUsername;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Autowired
    private RedisConsumerGroupService redisConsumerGroupService;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        config.setUsername(redisUsername);
        config.setPassword(redisPassword);

        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisObjectTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());

        // Configure serializers (e.g., JSON serializer)
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(MapRecord.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());

        template.afterPropertiesSet();

        return template;
    }

    @Bean
    public Subscription subscription(RedisConnectionFactory connectionFactory) throws UnknownHostException {

        redisConsumerGroupService.createConsumerGroupIfNotExists(connectionFactory, BUCKET_CREATE_EVENT_STREAM, REDIS_STREAM_SERVER_GROUP);

        StreamOffset<String> streamOffset = StreamOffset.create(BUCKET_CREATE_EVENT_STREAM, ReadOffset.lastConsumed());

        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String,
                ObjectRecord<String, S3Event>> options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofMillis(100))
                .targetType(S3Event.class)
                .build();

        StreamMessageListenerContainer<String, ObjectRecord<String, S3Event>>  container =
                StreamMessageListenerContainer
                        .create(connectionFactory, options);

        Subscription subscription =
                container.receive(Consumer.from(REDIS_STREAM_SERVER_GROUP, InetAddress.getLocalHost().getHostName()),
                        streamOffset, purchaseStreamListener());

        container.start();
        return subscription;
    }

    @Bean
    public StreamListener<String, ObjectRecord<String, S3Event>> purchaseStreamListener() {
        // handle message from stream
        return new RedisS3EventMessageProcessor();
    }
}