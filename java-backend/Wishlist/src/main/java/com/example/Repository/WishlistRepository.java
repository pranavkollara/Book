package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
	Wishlist findByUsername(String username);
}
