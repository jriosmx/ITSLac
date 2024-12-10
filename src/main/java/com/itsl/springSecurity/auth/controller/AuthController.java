package com.itsl.springSecurity.auth.controller;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itsl.springSecurity.dto.AuthRequest;
import com.itsl.springSecurity.entity.ERole;
import com.itsl.springSecurity.entity.Role;
import com.itsl.springSecurity.entity.User;
import com.itsl.springSecurity.payload.request.SignupRequest;
import com.itsl.springSecurity.repository.RoleRepository;
import com.itsl.springSecurity.repository.UserRepository;
import com.itsl.springSecurity.service.JwtService;
import com.itsl.springSecurity.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder encoder;	
	
	@Autowired
	RoleRepository roleRepository;

	@PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
	
//		System.out.println("authRequest.getUsername(): "+ authRequest.getUsername());
//		System.out.println("authRequest.getPassword(): "+ authRequest.getPassword());
//		
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken( authRequest.getUsername(),
						authRequest.getPassword() )
				);
		
		
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	        
    }
   
    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody User user) throws Exception {
    	
    	userService.addUser(user);
    	
        return ResponseEntity.ok("User registered successfully!");           
    }
    
    @PostMapping("/signup")
	public ResponseEntity<String> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
				
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body("Error: Username is already taken!");
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body("Error: Email is already in use!");
		}

		// Create new user's account
		User user = new User(signUpRequest.getName(),
							 signUpRequest.getLastname(),
							 signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));
		
		System.out.println("signUpRequest.getRole(): "+signUpRequest.getRole());


		String strRol = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();		

		if (strRol == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found. 1"));
			roles.add(userRole);
		} else {
			
				switch (strRol) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found. 2"));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found. 3"));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found. 4"));
					roles.add(userRole);
				}
			
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok("User registered successfully!");
	}
    

}
