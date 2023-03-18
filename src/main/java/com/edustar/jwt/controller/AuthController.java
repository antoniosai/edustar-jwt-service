package com.edustar.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edustar.jwt.model.AuthTokenModel;
import com.edustar.jwt.service.AuthService;



@RestController
@RequestMapping("/jwt/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("generate")
    public ResponseEntity<AuthTokenModel> getJWTToken(@RequestHeader("apikey") String apiKey)
    {
        return ResponseEntity.ok(authService.validateApiKeyAndGetJwtToken(apiKey));
    }

}