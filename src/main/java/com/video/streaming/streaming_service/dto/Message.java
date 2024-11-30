package com.video.streaming.streaming_service.dto;

import java.util.Date;

public record Message(String id, String content, Date createdAt) {
}
