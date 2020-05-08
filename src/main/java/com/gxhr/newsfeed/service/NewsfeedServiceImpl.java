package com.gxhr.newsfeed.service;

import com.datastax.driver.core.utils.UUIDs;
import com.gxhr.newsfeed.model.UserFeed;
import com.gxhr.newsfeed.model.UserFeedResponse;
import com.gxhr.newsfeed.repository.NewsfeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class NewsfeedServiceImpl implements NewsfeedService {

    @Autowired
    private NewsfeedRepository newsfeedRepository;

    @Override
    public List<UserFeedResponse> findAllByUserId(UUID userId) {
        List<UserFeed> tmp = newsfeedRepository.findByKeyUserId(userId);
        List<UserFeedResponse> tmp2 = new ArrayList<UserFeedResponse>();
        tmp.forEach(x -> {
            long ts = UUIDs.unixTimestamp(x.getKey().getTimestamp());
            UserFeedResponse ufr = UserFeedResponse.builder()
                    .actorId(x.getActorId())
                    .objectId(x.getObjectId())
                    .target(determineTarget(x.getTargetId().toString()))
                    .timestamp(ts)
                    .verb(determineVerb(x.getVerbId().toString()))
                    .build();
            tmp2.add(ufr);
        });
        return tmp2;
    }

    private String determineTarget(String id) {
        if (id.equals("76aa7d76-d942-411e-b440-ca38546b3802")) {
            return "pabili";
        }
        return "unknown";
    }

    private String determineVerb(String id) {
        if (id.equals("3d842474-33d6-4301-b764-6c2110cc327a")) {
            return "requested";
        }
        return "unknown";
    }

    /*
    @Override
    public Page<UserFeed> getFeedByUserId(UUID userId, int page, int size) {
        Pageable limit = PageRequest.of(page,size);
        List<UUID> id = Arrays.asList(userId);
        return newsfeedRepository.findAllById(id);
    }

     */
}
