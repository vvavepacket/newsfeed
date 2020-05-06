package com.gxhr.newsfeed.service;

import com.gxhr.newsfeed.model.UserFeed;
import com.gxhr.newsfeed.repository.NewsfeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class NewsfeedServiceImpl implements NewsfeedService {

    @Autowired
    private NewsfeedRepository newsfeedRepository;

    @Override
    public List<UserFeed> findAllByUserId(UUID userId) {
        return newsfeedRepository.findByKeyUserId(userId);
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
