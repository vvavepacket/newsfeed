package com.gxhr.newsfeed.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("app.place")
@Data
@Validated
public class CustomProperties {
    private String auth = "";
}
