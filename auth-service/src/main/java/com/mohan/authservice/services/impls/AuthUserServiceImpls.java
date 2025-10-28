package com.mohan.authservice.services.impls;

import com.mohan.authservice.dtos.APIResponse;
import com.mohan.authservice.dtos.LoginDto;
import com.mohan.authservice.dtos.RegisterDto;
import com.mohan.authservice.models.AuthUser;
import com.mohan.authservice.repositories.AuthUserRepository;
import com.mohan.authservice.services.AuthUserService;
import com.mohan.authservice.services.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserServiceImpls implements AuthUserService {

    private final AuthUserRepository authUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    @Override
    public APIResponse<String> register(RegisterDto registerDto) {
        APIResponse<String> response = new APIResponse<>();
        // check user is exists by username
        if (authUserRepository.existsByUsername(registerDto.getUsername())) {
            response.setMessage("Registration Failed");
            response.setStatusCode(409);
            response.setData("Username is already Exists");
            return response;
        }
        // check user is exists by email
        if (authUserRepository.existsByEmail(registerDto.getEmail())) {
            response.setMessage("Registration Failed");
            response.setStatusCode(409);
            response.setData("Email is already exists");
            return response;
        }
        // encode password and set role_
        AuthUser authUser = modelMapper.map(registerDto, AuthUser.class);
        authUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        authUser.setRole("ROLE_" + registerDto.getRole().toUpperCase());
        // save to db and send backs the response
        authUserRepository.save(authUser);
        response.setMessage("Registration Successful");
        response.setStatusCode(201);
        response.setData("New User is Created");
        return response;
    }

    @Override
    public APIResponse<String> login(LoginDto loginDto) {
        APIResponse<String> response = new APIResponse<>();
        // Get username and password
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()
        );

        Authentication authentication = authenticationManager.authenticate(token);

        if (authentication.isAuthenticated()) {
            String jwtToken = jwtService.generateJwtToken(
                    loginDto.getUsername(), authentication.getAuthorities().iterator().next().getAuthority());

            response.setMessage("Login Successful");
            response.setStatusCode(200);
            response.setData(jwtToken);
        } else {
            response.setMessage("Login Failed");
            response.setStatusCode(409);
            response.setData("Something went wrong");
        }
        log.info("{}", response);
        return response;
    }
}
