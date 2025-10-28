package com.mohan.authservice.services;

import com.mohan.authservice.dtos.APIResponse;
import com.mohan.authservice.dtos.LoginDto;
import com.mohan.authservice.dtos.RegisterDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthUserService {

    APIResponse<String> register(RegisterDto registerDto);
    APIResponse<String> login(LoginDto loginDto);
}
