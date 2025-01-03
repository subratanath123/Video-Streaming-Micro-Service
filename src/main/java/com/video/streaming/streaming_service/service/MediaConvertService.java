package com.video.streaming.streaming_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.mediaconvert.MediaConvertClient;
import software.amazon.awssdk.services.mediaconvert.model.*;

import static com.video.streaming.streaming_service.constants.Constants.MEDIA_CONVERT_ROLE_ARN;

@Service
public class MediaConvertService {

    @Autowired
    private MediaConvertClient mediaConvertClient;

    public String createMediaConvertJob(String inputFilePath,
                                        String outputFilePath,
                                        String videoJobTemplate) {

        Input input = Input.builder()
                .fileInput(inputFilePath)
                .build();

        CreateJobResponse createJobResponse = mediaConvertClient.createJob(jobReqBuilder ->
                jobReqBuilder
                        .role(MEDIA_CONVERT_ROLE_ARN)
                        .jobTemplate(videoJobTemplate)
                        .settings(
                                JobSettings
                                        .builder()
                                        .inputs(input)
                                        .build()
                        )
        );

        System.out.println("MediaConvert job created with ARN: " + createJobResponse.job().arn());

        return createJobResponse.job().arn();
    }

}
