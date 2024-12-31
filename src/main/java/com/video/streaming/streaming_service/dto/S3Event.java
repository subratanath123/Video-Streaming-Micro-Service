package com.video.streaming.streaming_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class S3Event implements Serializable {

    @JsonProperty("version")
    private String version;

    @JsonProperty("id")
    private String id;

    @JsonProperty("detail-type")
    private String detailType;

    @JsonProperty("source")
    private String source;

    @JsonProperty("account")
    private String account;

    @JsonProperty("time")
    private String time;

    @JsonProperty("region")
    private String region;

    @JsonProperty("resources")
    private List<String> resources;

    @JsonProperty("detail")
    private Detail detail;

    public S3Event() {
    }

    // Constructor
    public S3Event(String version, String id, String detailType, String source, String account,
                   String time, String region, List<String> resources, Detail detail) {
        this.version = version;
        this.id = id;
        this.detailType = detailType;
        this.source = source;
        this.account = account;
        this.time = time;
        this.region = region;
        this.resources = resources;
        this.detail = detail;
    }

    // Getters and Setters
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    // Inner Detail class
    public static class Detail {

        @JsonProperty("version")
        private String version;

        @JsonProperty("bucket")
        private Bucket bucket;

        @JsonProperty("object")
        private ObjectData object;

        @JsonProperty("request-id")
        private String requestId;

        @JsonProperty("requester")
        private String requester;

        @JsonProperty("source-ip-address")
        private String sourceIpAddress;

        @JsonProperty("reason")
        private String reason;

        public Detail() {
        }

        // Constructor
        public Detail(String version, Bucket bucket, ObjectData object, String requestId, String requester,
                      String sourceIpAddress, String reason) {
            this.version = version;
            this.bucket = bucket;
            this.object = object;
            this.requestId = requestId;
            this.requester = requester;
            this.sourceIpAddress = sourceIpAddress;
            this.reason = reason;
        }

        // Getters and Setters
        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Bucket getBucket() {
            return bucket;
        }

        public void setBucket(Bucket bucket) {
            this.bucket = bucket;
        }

        public ObjectData getObject() {
            return object;
        }

        public void setObject(ObjectData object) {
            this.object = object;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public String getRequester() {
            return requester;
        }

        public void setRequester(String requester) {
            this.requester = requester;
        }

        public String getSourceIpAddress() {
            return sourceIpAddress;
        }

        public void setSourceIpAddress(String sourceIpAddress) {
            this.sourceIpAddress = sourceIpAddress;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    // Inner Bucket class
    public static class Bucket {

        @JsonProperty("name")
        private String name;

        public Bucket() {
        }

        // Constructor
        public Bucket(String name) {
            this.name = name;
        }

        // Getter and Setter
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    // Inner ObjectData class
    public static class ObjectData {

        @JsonProperty("key")
        private String key;

        @JsonProperty("size")
        private long size;

        @JsonProperty("etag")
        private String etag;

        @JsonProperty("sequencer")
        private String sequencer;

        public ObjectData() {
        }

        // Constructor
        public ObjectData(String key, long size, String etag, String sequencer) {
            this.key = key;
            this.size = size;
            this.etag = etag;
            this.sequencer = sequencer;
        }

        // Getters and Setters
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public String getEtag() {
            return etag;
        }

        public void setEtag(String etag) {
            this.etag = etag;
        }

        public String getSequencer() {
            return sequencer;
        }

        public void setSequencer(String sequencer) {
            this.sequencer = sequencer;
        }
    }
}
