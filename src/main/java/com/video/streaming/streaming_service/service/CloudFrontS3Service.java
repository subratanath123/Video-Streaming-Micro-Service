package com.video.streaming.streaming_service.service;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import static com.video.streaming.streaming_service.constants.Constants.AWS_VIDEO_OUTPUT_BUCKET_NAME;

@Service
public class CloudFrontS3Service {

    @Value("${aws.cloudfront.public.key.id}")
    private String awsPublicKeyId;

    @Value("${aws.cloudfront.distribution.domain}")
    private String distributionDomain;

    @Autowired
    private AmazonS3 amazonS3;

    public void downloadSignedM3U8File(String objectKey, HttpServletResponse response) {
        Date expirationDate = new Date(System.currentTimeMillis() + 86400000);
        response.setContentType("application/vnd.apple.mpegurl");
        String basePath = objectKey.substring(0, objectKey.lastIndexOf("/") + 1);

        try {
            File cloudFrontPrivateKeyFile = generateCloudFrontPrivateKeyFile();
            S3Object m3U8File = getAwsS3File(objectKey);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(m3U8File.getObjectContent()));
                 PrintWriter writer = response.getWriter()) {

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.endsWith(".ts")) {
                        String tsFile = line.trim();
                        String tsKey = basePath + tsFile;

                        String signedUrl = CloudFrontUrlSigner.getSignedURLWithCannedPolicy(
                                SignerUtils.Protocol.https,
                                distributionDomain,
                                cloudFrontPrivateKeyFile,
                                tsKey,
                                awsPublicKeyId,
                                expirationDate
                        );
                        writer.println(signedUrl);
                    } else {
                        writer.println(line);
                    }
                }
            }

        } catch (IOException | InvalidKeySpecException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private File generateCloudFrontPrivateKeyFile() {
        return new File("/usr/local/aws/private_key.pem");
    }

    private S3Object getAwsS3File(String key) {
        return amazonS3.getObject(AWS_VIDEO_OUTPUT_BUCKET_NAME, key);
    }
}
X