package com.itsl.springSecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itsl.springSecurity.entity.User;
import com.itsl.springSecurity.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	 UserRepository repository;
	 
	 @Autowired
	 PasswordEncoder passwordEncoder;

	 public String addUser(User user) {
		 user.setPassword(passwordEncoder.encode(user.getPassword()));
	     repository.save(user);
	     return "user added to system ";
	 }
	
}
