package com.gxhr.newsfeed.repository;

import com.gxhr.newsfeed.model.UserFeed;
import com.gxhr.newsfeed.model.UserFeedKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "newsfeed", path = "newsfeed")
public interface NewsfeedRepository extends CassandraRepository<UserFeed, UserFeedKey> {

    List<UserFeed> findByKeyUserId(UUID userId);

}
