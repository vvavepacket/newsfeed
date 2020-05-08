package com.gxhr.newsfeed.service;

import com.gxhr.newsfeed.model.UserFeed;
import com.gxhr.newsfeed.model.UserFeedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface NewsfeedService {

    List<UserFeedResponse> findAllByUserId(UUID userId);

}
