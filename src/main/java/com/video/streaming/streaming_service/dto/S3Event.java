package com.video.streaming.streaming_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public record S3Event(
        @JsonProperty("version") String version,
        @JsonProperty("id") String id,
        @JsonProperty("detail-type") String detailType,
        @JsonProperty("source") String source,
        @JsonProperty("account") String account,
        @JsonProperty("time") String time,
        @JsonProperty("region") String region,
        @JsonProperty("resources") List<String> resources,
        @JsonProperty("detail") Detail detail
) {
    public static record Detail(
            @JsonProperty("version") String version,
            @JsonProperty("bucket") Bucket bucket,
            @JsonProperty("object") ObjectData object,
            @JsonProperty("request-id") String requestId,
            @JsonProperty("requester") String requester,
            @JsonProperty("source-ip-address") String sourceIpAddress,
            @JsonProperty("reason") String reason
    ) {}

    public static record Bucket(
            @JsonProperty("name") String name
    ) {}

    public static record ObjectData(
            @JsonProperty("key") String key,
            @JsonProperty("size") long size,
            @JsonProperty("etag") String etag,
            @JsonProperty("sequencer") String sequencer
    ) {}
}
