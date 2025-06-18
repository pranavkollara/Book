	package com.example.Service;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

	private final String SECRET_KEY = "jsonkeyaskjdjfhpisudhncpisoadncpai9sudhbcpaisdnhc";
	
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String,Object> claims = new HashMap<>();
		
		String authorities = userDetails.getAuthorities().stream().map(auth->auth.getAuthority()).collect(Collectors.joining("::"));
		claims.put("roles",authorities);
		
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).signWith(getSigningKey(),SignatureAlgorithm.HS256).compact();	
	}
	
	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	public Claims extractAllClaims(String token) throws Exception {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}
	
	public boolean isTokenValid(String token) {
		try {
			extractAllClaims(token);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	
}
