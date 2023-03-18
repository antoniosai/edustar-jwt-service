package com.edustar.jwt.service;

import com.edustar.jwt.model.AuthTokenModel;

public interface AuthService {

    public AuthTokenModel validateApiKeyAndGetJwtToken(String apiKey);

}
