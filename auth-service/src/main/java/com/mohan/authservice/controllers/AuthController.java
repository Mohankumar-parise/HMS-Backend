package com.mohan.authservice.controllers;

import com.mohan.authservice.dtos.APIResponse;
import com.mohan.authservice.dtos.LoginDto;
import com.mohan.authservice.dtos.RegisterDto;
import com.mohan.authservice.services.AuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUserService authUserService;

    // http://localhost:7070/api/v1/auth/register
    @PostMapping("/register")
    public ResponseEntity<APIResponse<String>> register(@RequestBody RegisterDto registerDto) {
        APIResponse<String> response = authUserService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
    // http://localhost:7070/api/v1/auth/register
    @PostMapping("/login")
    public ResponseEntity<APIResponse<String>> login(@RequestBody LoginDto loginDto) {
        APIResponse<String> response = authUserService.login(loginDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }
}
