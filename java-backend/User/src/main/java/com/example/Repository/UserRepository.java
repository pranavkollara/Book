package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.example.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	User findByUsername(String username);
}
