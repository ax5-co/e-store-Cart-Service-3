package com.execise.estore.estore.security;

import java.io.IOException;
import java.io.InputStream;

import static io.jsonwebtoken.Jwts.parser;


import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.User;
import com.execise.estore.estore.exception.EStoreException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {

	private KeyStore keyStore;
	
	@Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;
	
	@PostConstruct
	public void init() {
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceAsStream = getClass().
					getResourceAsStream("/springblog.jks");
			keyStore.load(resourceAsStream, "secret".toCharArray());
			
		} catch (KeyStoreException | CertificateException | 
				NoSuchAlgorithmException | IOException e) {
			throw new EStoreException("Exception occurred while "
					+ "loading keystore");
		}
	}
	
	
	public String generateToken(Authentication authentication) {
		User principal = (User) authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(principal.getUsername())
				.signWith(getPrivateKey())
				.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
				.compact();
	}
	
	public String generateTokenWithUserName(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(Date.from(Instant.now()))
				.signWith(getPrivateKey())
				.setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
				.compact();
	}
	
	private PrivateKey getPrivateKey() {
		try {
			return (PrivateKey) keyStore.getKey(
					"springblog", "secret".toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException | 
				UnrecoverableKeyException e) {
			throw new EStoreException("Exception occurred while retrieving "
					+ "public key from keystore");
		}
	}
	
	public boolean validateToken(String jwt) {
		parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
		return true;
	}

	// Using the alias of the keyStore I'm using, the springblog.jks. 
	//a different alias would be used depending on the used keyStore. 
	private PublicKey  getPublicKey() {
		try {
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e) {
            throw new EStoreException("Exception occured while retrieving public key from keystore");
        }
	}
	
	public String getUsernameFromJwt(String token) {
		Claims claims = parser()
				.setSigningKey(getPublicKey())
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getSubject();
	}
	public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
