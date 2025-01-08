package com.video.streaming.streaming_service.constants;

public class Constants {
    public static final String PAYLOAD_KEY = "payload";
    public static final String BUCKET_CREATE_EVENT_STREAM = "video-upload-bucket-event";
    public static final String REDIS_STREAM_SERVER_GROUP = "redis-stream-server-group-1";
    public static final String AWS_VIDEO_INPUT_BUCKET = "s3://video-input-bucket-final";
    public static final String AWS_VIDEO_OUTPUT_BUCKET_NAME = "video-output-bucket-final";
    public static final String AWS_VIDEO_OUTPUT_BUCKET = "s3://" + AWS_VIDEO_OUTPUT_BUCKET_NAME + "/output-folder/";
    public static final String AWS_VIDEO_JOB_TEMPLATE = "video-job-template";
    public static final String MEDIA_CONVERT_ROLE_ARN = "arn:aws:iam::242201311595:role/MediaConvertRole";
    public static final String OUTPUT_GROUP_HLS = "output-group-hls";

}
