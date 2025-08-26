package com.neobank.auth.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component // Makes this a Spring Bean that can be auto-wired in other classes
public class JwtUtil {
	
	// Secret key used to sign the JWT — keep it safe in real projects (not hardcoded)
	private final String SECRET = "NeoBankSecretKeyNeoBankSecretKey";
	
	// Token expiry time set to 10 hours (in milliseconds)
	private final long EXPIRATION_TIME = 1000*60*60*10;
	
	//Generates the secret signing key used to digitally sign your JWTs using HMAC-SHA256.
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
	}
	
	
	// Generates a JWT token using the username
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username) // Set username as the subject of the token
				.setIssuedAt(new Date(System.currentTimeMillis())) // Set token creation time
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set expiry
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact(); // Final token string						
	}
	
	// Extract username from the token (used in validation)
	public String extractUsername(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject(); // Subject is the username
	}
	
	
	
	// Check if token is valid for a given username
	public boolean  isTokenValid(String token, String username) {
		return extractUsername(token).equals(username) && !isTokenExpired(token);
	}
	
    
	
	// Check if token has expired
	public boolean isTokenExpired(String token) {
		Date expiration  = Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getExpiration();  // Extract expiration date
		return expiration.before(new Date()); // Compare with current time		
	}
	
	
	

}
