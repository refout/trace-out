package com.refout.trace.authentication.domain;


public record RegisterRequest(
        String username,
        String password,
        String nickname,
        String email,
        String phone,
        String gender,
        String avatar) {

}
