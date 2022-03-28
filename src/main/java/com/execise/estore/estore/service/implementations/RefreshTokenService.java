package com.execise.estore.estore.service.implementations;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.execise.estore.estore.entity.RefreshToken;
import com.execise.estore.estore.exception.EStoreException;
import com.execise.estore.estore.repository.RefreshTokenRepository;

@Service
@Transactional
public class RefreshTokenService {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	public RefreshToken generateRefreshToken() {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(UUID.randomUUID().toString()); //UUID will be 128 bit 
		refreshToken.setCreatedDate(Instant.now());
		
		return refreshTokenRepository.save(refreshToken);
	}
	
	void validateRefreshToken(String token) {
		refreshTokenRepository.findByToken(token)
			.orElseThrow(() -> new EStoreException("Invalid refresh Token"));
	}
	
	public void deleteRefreshToken(String token) {
		refreshTokenRepository.deleteByToken(token);
	}
}
