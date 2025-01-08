package com.video.streaming.streaming_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.mediaconvert.MediaConvertClient;
import software.amazon.awssdk.services.mediaconvert.model.*;

import static com.video.streaming.streaming_service.constants.Constants.MEDIA_CONVERT_ROLE_ARN;
import static com.video.streaming.streaming_service.constants.Constants.OUTPUT_GROUP_HLS;
import static software.amazon.awssdk.services.mediaconvert.model.OutputGroupType.FILE_GROUP_SETTINGS;
import static software.amazon.awssdk.services.mediaconvert.model.OutputGroupType.HLS_GROUP_SETTINGS;

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

        CreateJobResponse createJobResponse = mediaConvertClient.createJob(jobReqBuilder -> jobReqBuilder
                .role(MEDIA_CONVERT_ROLE_ARN)
                .jobTemplate(videoJobTemplate)
                .settings(JobSettings.builder()
                        .inputs(input)
                        .outputGroups(
                                OutputGroup
                                        .builder()
                                        .customName(OUTPUT_GROUP_HLS)
                                        .outputGroupSettings(
                                                OutputGroupSettings
                                                        .builder()
                                                        .type(HLS_GROUP_SETTINGS)
                                                        .hlsGroupSettings(
                                                                HlsGroupSettings
                                                                        .builder()
                                                                        .destination(outputFilePath)
                                                                        .build()
                                                        )
                                                        .build()
                                        )
                                        .build())
                        .build())
        );

        System.out.println("MediaConvert job created with ARN: " + createJobResponse.job().arn());

        return createJobResponse.job().arn();
    }

}
