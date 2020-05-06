package com.gxhr.newsfeed.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    @PrimaryKey
    private UUID activityId;
    private UUID actorId;
    private UUID verbId;
    private UUID objectId;
    private UUID targetId;
    private UUID timestamp;

}
