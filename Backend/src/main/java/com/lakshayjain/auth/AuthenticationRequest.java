package com.lakshayjain.auth;

public record AuthenticationRequest (
        String username,
        String password
){
}
