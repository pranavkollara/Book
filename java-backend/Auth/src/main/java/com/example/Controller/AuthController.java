package com.example.Controller;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Request.AuthRequest;
import com.example.Service.JwtService;
import com.example.Service.UserService;

@RestController
public class AuthController {

	@Autowired
	UserService userService;
	
	@Autowired
	JwtService jwtService;
	
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody AuthRequest authReq) {
	return ResponseEntity.ok(userService.createUser(authReq.getUsername(), authReq.getPassword()));
	
	}
	
	
	@PostMapping("/registerAuthor")
	public ResponseEntity<String> registerAuthor(@RequestBody AuthRequest authReq) {
	return ResponseEntity.ok(userService.createAuthor(authReq.getUsername(), authReq.getPassword()));
	
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/verifyAdmin")
	public boolean verifyAdmin() {
		return true;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/registerAdmin")
	public ResponseEntity<String> registerAdmin(@RequestBody AuthRequest authReq) {
	return ResponseEntity.ok(userService.createAdmin(authReq.getUsername(), authReq.getPassword()));
	
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody AuthRequest authReq){
		
		UserDetails user = userService.loadUserByUsername(authReq.getUsername());
		if(userService.validatePassword(authReq.getUsername(), authReq.getPassword())) {
			String token = jwtService.generateToken(user);
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
		
	}
	
	@GetMapping("/userRole/{username}")
	public Collection<? extends GrantedAuthority> userRole(@PathVariable String username){
		return userService.loadUserByUsername(username).getAuthorities();
	}
	
	@GetMapping("/userRole")
	public Collection<? extends GrantedAuthority> userRoleByToken(Principal principal){
		return userService.loadUserByUsername(principal.getName()).getAuthorities();
	}
	
	@GetMapping("/loadUser/{username}")
	public ResponseEntity<UserDetails> loadUser(@PathVariable String username){
		return ResponseEntity.ok(userService.loadUserByUsername(username));
	}
	
	@GetMapping("/loadUser")
	public ResponseEntity<UserDetails> loadUserByToken(Principal principal){
		return ResponseEntity.ok(userService.loadUserByUsername(principal.getName()));
	}
	
	
}
