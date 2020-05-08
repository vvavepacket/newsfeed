package com.gxhr.newsfeed.controller;

import com.datastax.driver.core.utils.UUIDs;
import com.gxhr.newsfeed.model.Activity;
import com.gxhr.newsfeed.model.UserFeed;
import com.gxhr.newsfeed.model.UserFeedResponse;
import com.gxhr.newsfeed.repository.NewsfeedRepository;
import com.gxhr.newsfeed.service.ActivityService;
import com.gxhr.newsfeed.service.NewsfeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class NewsfeedController {

    //@Autowired
    //private NewsfeedRepository newsfeedRepository;

    @Autowired
    private NewsfeedService newsfeedService;

    @Autowired
    private ActivityService activityService;

    /*
    @PostConstruct
    public void init() {

        //private UUID userId;
        //private UUID actorId;
        //private UUID verbId;
        //private UUID objectId;
        //private UUID targetId;
        //private UUID timestamp;

        List<UserFeed> feeds = new ArrayList<>();
        feeds.add(new UserFeed(UUID.fromString("27cb07e7-db89-48dc-a441-989d971829d6"), UUID.fromString("1705a76b-4258-4682-b788-2ddff9d00a6e"), UUID.fromString("e59ba7a4-c4a5-4588-b36a-8a2fb4092813"), UUID.fromString("c6c908f8-1eca-43ab-bcd5-dcab712fe0ba"), UUID.fromString("5cd65dec-71ab-4b52-b593-4cedd83c3755"), UUIDs.timeBased()));
        feeds.add(new UserFeed(UUID.fromString("27cb07e7-db89-48dc-a441-989d971829d6"), UUID.fromString("1705a76b-4258-4682-b788-2ddff9d00a6e"), UUID.fromString("e59ba7a4-c4a5-4588-b36a-8a2fb4092813"), UUID.fromString("c6c908f8-1eca-43ab-bcd5-dcab712fe0ba"), UUID.fromString("5cd65dec-71ab-4b52-b593-4cedd83c3755"), UUIDs.timeBased()));
        feeds.add(new UserFeed(UUID.fromString("27cb07e7-db89-48dc-a441-989d971829d6"), UUID.fromString("1705a76b-4258-4682-b788-2ddff9d00a6e"), UUID.fromString("e59ba7a4-c4a5-4588-b36a-8a2fb4092813"), UUID.fromString("c6c908f8-1eca-43ab-bcd5-dcab712fe0ba"), UUID.fromString("5cd65dec-71ab-4b52-b593-4cedd83c3755"), UUIDs.timeBased()));
        feeds.add(new UserFeed(UUID.fromString("27cb07e7-db89-48dc-a441-989d971829d6"), UUID.fromString("1705a76b-4258-4682-b788-2ddff9d00a6e"), UUID.fromString("e59ba7a4-c4a5-4588-b36a-8a2fb4092813"), UUID.fromString("c6c908f8-1eca-43ab-bcd5-dcab712fe0ba"), UUID.fromString("5cd65dec-71ab-4b52-b593-4cedd83c3755"), UUIDs.timeBased()));
        feeds.add(new UserFeed(UUID.fromString("27cb07e7-db89-48dc-a441-989d971829d6"), UUID.fromString("1705a76b-4258-4682-b788-2ddff9d00a6e"), UUID.fromString("e59ba7a4-c4a5-4588-b36a-8a2fb4092813"), UUID.fromString("c6c908f8-1eca-43ab-bcd5-dcab712fe0ba"), UUID.fromString("5cd65dec-71ab-4b52-b593-4cedd83c3755"), UUIDs.timeBased()));

        newsfeedRepository.saveAll(feeds);
    }

     */

    @GetMapping("/user/{userId}")
    public List<UserFeedResponse> getNewsfeedsByUserId(@PathVariable String userId) {
        return newsfeedService.findAllByUserId(UUID.fromString(userId));
    }

    @PostMapping("/activity")
    public ResponseEntity createActivity(@RequestBody Activity activity) {
        UUID random = UUID.randomUUID();
        activity.setActivityId(random);
        activity.setTimestamp(UUIDs.timeBased());
        // send the activity to all users feed
        return ResponseEntity.ok(activityService.createActivity(activity));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity getActivityById(@PathVariable UUID activityId) {
        Optional<Activity> tmp = activityService.findById(activityId);
        if (tmp.isPresent()) {
            return ResponseEntity.ok(tmp);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*
    @PostMapping("/user/{userId}")
    ResponseEntity pushFeed(@RequestBody UserFeed feed) throws IOException {

    }


     */
}
