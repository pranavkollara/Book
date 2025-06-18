package com.example.Wishlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;




@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.Repository")
@EntityScan(basePackages = "com.example.Model")
@ComponentScan(basePackages = {"com.example.Controller","com.example.Service","com.example.Filter","com.example.Security","com.example.Config"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.example.Feign")

public class WishlistApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(WishlistApplication.class, args);
	}

}

