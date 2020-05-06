package com.gxhr.newsfeed.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String identityId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dob;
}
