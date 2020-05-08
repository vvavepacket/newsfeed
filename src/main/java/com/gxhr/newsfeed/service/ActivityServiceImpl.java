package com.gxhr.newsfeed.service;

import com.datastax.driver.core.utils.UUIDs;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gxhr.newsfeed.config.CustomProperties;
import com.gxhr.newsfeed.model.Activity;
import com.gxhr.newsfeed.model.User;
import com.gxhr.newsfeed.model.UserFeed;
import com.gxhr.newsfeed.model.UserFeedKey;
import com.gxhr.newsfeed.repository.ActivityRepository;
import com.gxhr.newsfeed.repository.NewsfeedRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActivityServiceImpl implements ActivityService {

    Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private NewsfeedRepository newsfeedRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomProperties customProperties;

    @Override
    public Optional<Activity> findById(UUID activityId) {
        return activityRepository.findById(activityId);
    }

    @Override
    public Activity createActivity(Activity activity) {
        pushToFeeds(activity);
        return activityRepository.save(activity);
    }

    @Override
    public void pushToFeeds(Activity activity) {
        // TODO
        // possible bottleneck in the future, make this async...
        // if we have millions of users, this will loop and take long time to finish
        // query auth microservice to get all uuids
        try {

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(customProperties.getAuth() + "/users");
            HttpResponse httpresponse = httpclient.execute(httpget);
            String json_string = EntityUtils.toString(httpresponse.getEntity());

            JSONParser parser = new JSONParser();
            JSONArray array = (JSONArray) parser.parse(json_string);

            // for each user, push a feed to them
            for(int i=0; i<array.size(); i++) {
                User user = objectMapper.convertValue(array.get(i), User.class);
                //logger.info(user.getIdentityId());
                // use activity's timestamp and userid as key for the userfeed
                UserFeedKey key = UserFeedKey.builder()
                        .userId(UUID.fromString(user.getIdentityId().replace("us-east-1:", "")))
                        .timestamp(activity.getTimestamp())
                        .build();
                UserFeed tmp = UserFeed.builder()
                        .key(key)
                        .actorId(activity.getActorId())
                        .objectId(activity.getObjectId())
                        .targetId(activity.getTargetId())
                        .verbId(activity.getVerbId())
                        .build();
                newsfeedRepository.save(tmp);
            }




        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
