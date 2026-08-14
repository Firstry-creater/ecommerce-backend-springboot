package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.dto.request.LoginRequest;
import com.example.ecommercebackend.dto.request.RegisterRequest;
import com.example.ecommercebackend.dto.response.AuthResponse;
import com.example.ecommercebackend.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public AuthResponse register(
           @Valid @RequestBody  RegisterRequest request){

        System.out.println("Register API called");

        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public  AuthResponse login(@Valid @RequestBody LoginRequest request){

        System.out.println("Login API called");

        return  authenticationService.login(request);
    }
}
