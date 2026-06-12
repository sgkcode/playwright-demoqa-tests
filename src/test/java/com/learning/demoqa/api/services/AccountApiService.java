package com.learning.demoqa.api.services;

import com.learning.demoqa.api.models.request.UserCredentialsRequestModel;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AccountApiService extends BaseApiService {

    public AccountApiService(String baseUrl) {
        super(baseUrl);
    }

    public Response createUser(UserCredentialsRequestModel credentials) {
        return given(spec)
                .body(credentials)
                .when()
                .post("/Account/v1/User");
    }

    public Response generateToken(UserCredentialsRequestModel credentials) {
        return given(spec)
                .body(credentials)
                .when()
                .post("/Account/v1/GenerateToken");
    }
}
