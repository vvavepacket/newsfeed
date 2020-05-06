package com.gxhr.newsfeed.repository;

import com.gxhr.newsfeed.model.Activity;
import com.gxhr.newsfeed.model.UserFeed;
import com.gxhr.newsfeed.model.UserFeedKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "activity", path = "activity")
public interface ActivityRepository extends CassandraRepository<Activity, UUID> {



}
