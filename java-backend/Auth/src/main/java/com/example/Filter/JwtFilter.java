package com.example.Filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Service.JwtService;
import com.example.Service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	UserService userService;
	
	public JwtFilter() {
	    System.out.println("JwtFilter bean created");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		String jwt;
		String username;
		
		System.out.println(authHeader);
		if(authHeader==null||!authHeader.startsWith("Bearer ")) {
			System.out.println("Error");
			filterChain.doFilter(request, response);
			return;
		}
		
		
		
		jwt = authHeader.substring(7);
		username = jwtService.extractUsername(jwt);
		System.out.println(SecurityContextHolder.getContext().getAuthentication());
		System.out.println(username);
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			System.out.println("no auth yet");
			UserDetails user = userService.loadUserByUsername(username);
			System.out.println(jwtService.isTokenValid(jwt, user));
			if(jwtService.isTokenValid(jwt, user)) {
				String roles = null;
				System.out.println("valid token");
				try {
					roles = jwtService.extractAllClaims(jwt).get("roles",String.class);
					System.out.println(roles);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<SimpleGrantedAuthority> list = List.of();
				if(roles!=null&& !roles.isEmpty()) {
					list = Arrays.stream(roles.split("::")).map(SimpleGrantedAuthority::new).toList();
				}
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user,null,list);
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
		
	}

	
	
	
}
