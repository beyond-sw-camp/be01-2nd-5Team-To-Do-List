package com.example.team5_project.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.team5_project.Service.AuthService;
import com.example.team5_project.model.LoginDto;
import com.example.team5_project.model.TokenDto;
import com.example.team5_project.security.JwtFilter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

    	String jwt = authService.authorize(loginDto);
    	HttpHeaders httpHeaders = new HttpHeaders();
    	// response header에 jwt token에 넣어줌
    	httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, jwt);

    	
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.OK);
    }
}
