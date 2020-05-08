package com.gxhr.newsfeed.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFeedResponse {
    private long timestamp;
    private UUID actorId;
    private String verb;
    private UUID objectId;
    private String target;
}
