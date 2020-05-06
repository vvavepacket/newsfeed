package com.gxhr.newsfeed.service;

import com.gxhr.newsfeed.model.Activity;
import com.gxhr.newsfeed.repository.ActivityRepository;
import com.gxhr.newsfeed.repository.NewsfeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public Optional<Activity> findById(UUID activityId) {
        return activityRepository.findById(activityId);
    }

    @Override
    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

}
