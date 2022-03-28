package com.execise.estore.estore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.execise.estore.estore.dto.AuthenticationResponse;
import com.execise.estore.estore.dto.LoginRequest;
import com.execise.estore.estore.dto.RefreshTokenRequest;
import com.execise.estore.estore.dto.RegisterRequest;
import com.execise.estore.estore.service.implementations.AuthService;
import com.execise.estore.estore.service.implementations.RefreshTokenService;

import lombok.AllArgsConstructor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
		authService.signup(registerRequest);
		return new ResponseEntity<>("User Registration Successful", 
				HttpStatus.OK);
	}
	
	@GetMapping("account-verification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successully", HttpStatus.OK);
    }
	
	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest);
	
	}
	
	//typically, if you want an input/parameter to be validated
	//based on its validation constraints (defined in its class by annotations)
	//then when passing it, you use @Valid as below
	//but in our project we did not care much to be simple.
	
	@PostMapping("refresh/token")
	public AuthenticationResponse refreshTokens(
			@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
		
		return authService.refreshToken(refreshTokenRequest);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout (
			@Valid @RequestBody RefreshTokenRequest refreshTokenRequesty ){
		refreshTokenService.deleteRefreshToken(refreshTokenRequesty.getRefreshToken());
		return ResponseEntity
					.status(HttpStatus.OK)
					.body("Refresh Token Deleted Successfully");
	}
}
