package com.learning.demoqa.api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialsRequestModel {

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;
}
