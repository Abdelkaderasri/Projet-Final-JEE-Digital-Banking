package com.example.DigitalBanking.models;

import java.util.List;

public class AuthenticationResponse {
    private final String jwt;

    public AuthenticationResponse(String jwt, List<String> roles) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
