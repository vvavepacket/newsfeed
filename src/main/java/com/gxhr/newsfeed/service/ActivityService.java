package com.gxhr.newsfeed.service;

import com.gxhr.newsfeed.model.Activity;
import com.gxhr.newsfeed.model.UserFeed;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActivityService {

    Optional<Activity> findById(UUID activityId);
    Activity createActivity(Activity activity);

}
