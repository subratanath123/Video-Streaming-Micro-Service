package com.video.streaming.streaming_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.mediaconvert.MediaConvertClient;
import software.amazon.awssdk.services.mediaconvert.model.*;

@Service
public class MediaConvertService {

    @Autowired
    private MediaConvertClient mediaConvertClient;

    public String createMediaConvertJob(String inputFilePath, String outputFilePath, String videoJobTemplate) {
        Input input = Input.builder()
                .fileInput(inputFilePath)
                .build();

        // Build Output Group
        OutputGroup outputGroup = OutputGroup.builder()
                .customName("Video Output Group")
                .outputGroupSettings(
                        OutputGroupSettings.builder()
                                .type(OutputGroupType.FILE_GROUP_SETTINGS)
                                .fileGroupSettings(FileGroupSettings.builder()
                                        .destination(outputFilePath)
                                        .build())
                                .build()
                )
                .build();

        CreateJobResponse createJobResponse = mediaConvertClient.createJob(jobReqBuilder ->
                jobReqBuilder
                        .jobTemplate(videoJobTemplate)
                        .settings(
                                JobSettings
                                        .builder()
                                        .inputs(input)
                                        .outputGroups(outputGroup)
                                        .build()
                        )
        );

        System.out.println("MediaConvert job created with ARN: " + createJobResponse.job().arn());

        return createJobResponse.job().arn();
    }

}
