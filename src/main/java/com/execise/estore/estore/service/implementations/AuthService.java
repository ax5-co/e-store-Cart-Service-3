package com.execise.estore.estore.service.implementations;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.execise.estore.estore.dto.AuthenticationResponse;
import com.execise.estore.estore.dto.LoginRequest;
import com.execise.estore.estore.dto.RefreshTokenRequest;
import com.execise.estore.estore.dto.RegisterRequest;
import com.execise.estore.estore.entity.NotificationEmail;
import com.execise.estore.estore.entity.Role;
import com.execise.estore.estore.entity.User;
import com.execise.estore.estore.entity.VerificationToken;
import com.execise.estore.estore.exception.EStoreException;
import com.execise.estore.estore.repository.UserRepository;
import com.execise.estore.estore.repository.VerificationTokenRepository;
import com.execise.estore.estore.security.JwtProvider;
import com.execise.estore.estore.service.UserService;

import static com.execise.estore.estore.Constants.ACTIVATION_EMAIL;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {

	// tytpiaclly, we would be using @autowired for the next two instances (fields)
	// but, we will be following best practices and use something else called:
	// constrictor injection (or so)
	// so, we remove the @Autowired
	// put @AllArgsConstructor and make the fields final.
	// and use @Transactional above the method where we use the repository.

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private VerificationTokenRepository verificationTokenRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MailContentBuilder mailContentBuilder; 

	@Autowired
	private MailService mailService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private  JwtProvider jwtProvider;
	
	@Autowired
	private UserService userService;

	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@Transactional
	public void signup(RegisterRequest registerRequest) {

		User user = new User();
		user.setEmail(registerRequest.getEmail());
		user.setFirstName(registerRequest.getFirstName());
		user.setLastName(registerRequest.getLastName());
		user.setPasswordHash(encodePassword(registerRequest.getPassword()));
		user.setUserName(registerRequest.getUserName());
		user.setCreatedAt(Instant.now());
		user.setRole(Role.CUSTOMER);
		user.setEnabled(false);

		userService.save(user);
		//userRepository.save(user);

		String token = generateVerificationToken(user);
		String message = mailContentBuilder.build("Thank you for signing up to Spring Reddit, please click on the"
				+ " below url to activate your account : " + ACTIVATION_EMAIL + "/" + token);

		mailService.sendMail(new NotificationEmail("Please Activate your Account", user.getEmail(), message));
	}

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);

		verificationTokenRepository.save(verificationToken);
		return token;
	}

	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
		verificationTokenOptional.orElseThrow(() -> new EStoreException("Invalid Token"));
		fetchUserAndEnable(verificationTokenOptional.get());
	}

	@Transactional(readOnly = true)
	
	public User getCurrentUser() {
		org.springframework.security.core.userdetails.User principal = 
				(org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		return userRepository.findByUserName(principal.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("Username not found - " 
									+ principal.getUsername()));
	}

	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String userName = verificationToken.getUser().getUserName();
		User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new EStoreException("User Not Found with id - " + userName));
		user.setEnabled(true);
		userRepository.save(user);
	}

	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(), 
						loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String token = jwtProvider.generateToken(authenticate);

		return AuthenticationResponse.builder()
				.authenticationToken(token)
				.refreshToken(
						refreshTokenService.generateRefreshToken().getToken())
				.expiresAt(Instant.now()
						.plusMillis(jwtProvider.getJwtExpirationInMillis()))
				.userName(loginRequest.getUsername())
				.build();
	}

	public AuthenticationResponse refreshToken(@Valid RefreshTokenRequest refreshTokenRequest) {
		// validate the refreshToken passed here
		// if valid (no exceptions thrown) -> then generate a new one
		//and send it back
		
		refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
		
		String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
		
		return AuthenticationResponse.builder()
				.authenticationToken(token)
				.refreshToken(refreshTokenRequest.getRefreshToken())
				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
				.userName(refreshTokenRequest.getUsername())
				.build();
	}

}
