package com.edustar.jwt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edustar.jwt.config.PropertySource;
import com.edustar.jwt.exception.AuthException;
import com.edustar.jwt.model.AuthTokenModel;
import com.edustar.jwt.repo.DataStore;
import com.edustar.jwt.utils.JWTHelper;
import com.edustar.jwt.utils.TokenClaim;



@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private DataStore dataStore;

    @Autowired
    private PropertySource propertySource;

    @Override
    public AuthTokenModel validateApiKeyAndGetJwtToken(final String apiKey) {
        try {
            System.out.println("validateApiKey => " + apiKey);
            final String userId = validateApiKeyAndGetUserId(apiKey);

            System.out.println("userId => " + userId);
            final Map<String, Object> claims = getUserInfo(userId);

            System.out.println("claims => " + claims);
            final String jwtTokenValue = JWTHelper.createJWT(claims, propertySource.getAppName(),
                    propertySource.getAppAuthSecret(), propertySource.getAppTimeToLive());


            AuthTokenModel tokenModel = getTokenModel(jwtTokenValue);

            System.out.println("tokenModel => " + tokenModel);
            return tokenModel;
        } catch (Exception e) {
            throw new AuthException("sUnauthorized API key : " + apiKey, e);
        }
    }

    private String validateApiKeyAndGetUserId(final String apiKey) {
        return Optional.ofNullable(dataStore.getUserIdForApikey(apiKey))
                .orElseThrow(() -> new AuthException("InValid API Key"));
    }

    private Map<String, Object> getUserInfo(final String userId) {
        final Map<String, Object> claims = new HashMap<>();
        final String userInfo = dataStore.getUserInfo(userId);
        final String userRole = dataStore.getUserRole(userId);
        final List<String> authorities = new ArrayList<>();
        authorities.add(userRole);
        claims.put(TokenClaim.USER_ID.getKey(), userId);
        claims.put(TokenClaim.USER_INFO.getKey(), userInfo);
        claims.put(TokenClaim.AUTHORITIES.getKey(), authorities);
        return claims;
    }

    private AuthTokenModel getTokenModel(final String jwtTokenValue) {
        final AuthTokenModel tokenModel = new AuthTokenModel();
        tokenModel.setType(propertySource.getAppAuthTokenType());
        tokenModel.setToken(jwtTokenValue);
        return tokenModel;
    }

}