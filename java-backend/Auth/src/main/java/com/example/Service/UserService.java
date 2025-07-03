package com.example.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Model.User;
import com.example.Repository.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return userRepo.findByUsername(username);
	}
	
	
	@Transactional
	public String createUser(String username,String password) {
		
		if(userRepo.findByUsername(username)!=null) {
			return "User already exists";
		}
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		user.setAuthorities("ROLE_USER");
		
		userRepo.save(user);
		return "User Created";
	}
	
	@Transactional
	public String createAdmin(String username,String password) {
		
		if(userRepo.findByUsername(username)!=null) {
			return "Admin already exists";
		}
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		user.setAuthorities("ROLE_ADMIN");
		
		userRepo.save(user);
		return "Admin Created";
	}
	
	@Transactional
	public String createAuthor(String username,String password) {
		
		if(userRepo.findByUsername(username)!=null) {
			return "Author already exists";
		}
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		user.setAuthorities("ROLE_AUTHOR");
		
		userRepo.save(user);
		return "Author Created";
	}
	
	@Transactional
	public boolean validatePassword(String username,String password) {
		User user = userRepo.findByUsername(username);
		return new BCryptPasswordEncoder().matches(password,user.getPassword());
	}
	
	
	

}
