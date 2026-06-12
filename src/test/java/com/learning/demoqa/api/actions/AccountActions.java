package com.learning.demoqa.api.actions;

import com.learning.demoqa.api.models.request.UserCredentialsRequestModel;
import com.learning.demoqa.api.models.response.TokenResponseModel;
import com.learning.demoqa.api.services.AccountApiService;

public class AccountActions {

    private final AccountApiService accountService;

    public AccountActions(String baseUrl) {
        accountService = new AccountApiService(baseUrl);
    }

    public TokenResponseModel createUserAndGenerateToken(String userName, String password) {
        accountService.createUser(new UserCredentialsRequestModel(userName, password)); // 201 or 406 if already exists — both acceptable
        return generateTokenAndGetResponse(userName, password);
    }

    public TokenResponseModel generateTokenAndGetResponse(String userName, String password) {
        return accountService.generateToken(new UserCredentialsRequestModel(userName, password))
                .as(TokenResponseModel.class);
    }
}
