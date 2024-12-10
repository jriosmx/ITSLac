package com.itsl.springSecurity.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

 
public class SignupRequest {
	
	@NotNull
	private String name;

	@NotNull
	private String lastname;
	
    @Size(min = 3, max = 20)
    private String username;
 
    @Size(max = 50)
    @Email
    private String email;
    
    private String role;
    
    @Size(min = 6, max = 40)
    private String password;
    
    
    public SignupRequest() {				
	}
    
    public SignupRequest(@NotNull String name, @NotNull String lastname,
			@Size(min = 3, max = 20) String username, @Size(max = 50) @Email String email, String role,
			@Size(min = 6, max = 40) String password) {		
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.role = role;
		this.password = password;
	}

	public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
    
   
}
