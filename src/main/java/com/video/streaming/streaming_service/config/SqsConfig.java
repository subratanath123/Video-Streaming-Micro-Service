package com.video.streaming.streaming_service.config;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.mediaconvert.MediaConvertClient;
import software.amazon.awssdk.services.mediaconvert.model.DescribeEndpointsRequest;
import software.amazon.awssdk.services.mediaconvert.model.DescribeEndpointsResponse;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

//https://howtodoinjava.com/spring-cloud/aws-sqs-with-spring-cloud-aws/
@Configuration
public class SqsConfig {

    @Value("${spring.cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${spring.cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${spring.cloud.aws.region.static}")
    private String region;

    @Bean
    SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient
                .builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    @Bean
    MediaConvertClient mediaConvertClient() {
        Region region = Region.US_WEST_2;

        MediaConvertClient mc = MediaConvertClient.builder()
                .region(Region.US_WEST_2)
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();

        DescribeEndpointsResponse res = mc.describeEndpoints(DescribeEndpointsRequest.builder()
                .maxResults(20)
                .build());

        if (res.endpoints().size() == 0) {
            System.out.println("Cannot find MediaConvert service endpoint URL!");
            System.exit(1);
        }

        String endpointURL = res.endpoints().get(0).url();

        return MediaConvertClient.builder()
                .region(Region.US_WEST_2)
                .endpointOverride(URI.create(endpointURL))
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient) {
        return SqsTemplate.builder().sqsAsyncClient(sqsAsyncClient).build();
    }

}