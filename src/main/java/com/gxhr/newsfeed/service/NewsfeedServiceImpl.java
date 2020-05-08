package com.gxhr.newsfeed.service;

import com.datastax.driver.core.utils.UUIDs;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gxhr.newsfeed.config.CustomProperties;
import com.gxhr.newsfeed.model.User;
import com.gxhr.newsfeed.model.UserFeed;
import com.gxhr.newsfeed.model.UserFeedKey;
import com.gxhr.newsfeed.model.UserFeedResponse;
import com.gxhr.newsfeed.repository.NewsfeedRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomProperties customProperties;

    @Override
    public List<UserFeedResponse> findAllByUserId(UUID userId) {
        List<UserFeed> tmp = newsfeedRepository.findByKeyUserId(userId);
        List<UserFeedResponse> tmp2 = new ArrayList<UserFeedResponse>();
        tmp.forEach(x -> {
            long ts = UUIDs.unixTimestamp(x.getKey().getTimestamp());
            // retrieve user full name
            UserFeedResponse ufr = UserFeedResponse.builder()
                    .actorId(x.getActorId())
                    .objectId(x.getObjectId())
                    .target(determineTarget(x.getTargetId().toString()))
                    .timestamp(ts)
                    .verb(determineVerb(x.getVerbId().toString()))
                    .actorName(getUserFullname(x.getActorId().toString()))
                    .actorImg("https://data.whicdn.com/images/316527818/original.png")
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

    private String getUserFullname(String id) {
        String fullname = "";
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(customProperties.getAuth() + "/users/" + "us-east-1:" + id);
            HttpResponse httpresponse = httpclient.execute(httpget);
            String json_string = EntityUtils.toString(httpresponse.getEntity());

            JSONObject userObj = (JSONObject) new JSONParser().parse(json_string);
            String fname = userObj.getAsString("firstName");
            String lname = userObj.getAsString("lastName");
            fullname = fname + " " + lname;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fullname;
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
