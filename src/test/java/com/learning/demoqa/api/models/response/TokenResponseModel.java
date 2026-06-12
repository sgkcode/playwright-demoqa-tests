package com.learning.demoqa.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponseModel {

    @JsonProperty("status")
    private String status;

    @JsonProperty("token")
    private String token;

    @JsonProperty("result")
    private String result;
}
