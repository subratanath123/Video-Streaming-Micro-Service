package com.video.streaming.streaming_service.controller;

import com.video.streaming.streaming_service.service.CloudFrontS3Service;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CloudFrontController {

    @Autowired
    private CloudFrontS3Service cloudFrontS3Service;

    @GetMapping("/v1/cloudfront/stream/m3u8")
    public void downloadSignedM3U8(@RequestParam String objectKey,
                                   HttpServletResponse response) {

        cloudFrontS3Service.downloadSignedM3U8File(objectKey, response);
    }

}
