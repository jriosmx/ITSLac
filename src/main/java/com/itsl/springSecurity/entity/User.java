package com.itsl.springSecurity.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import jakarta.validation.constraints.Size;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity(name = "users")
@Table(	name = "users", 

uniqueConstraints = { 
	@UniqueConstraint(columnNames = "username"),
	@UniqueConstraint(columnNames = "email") 
})

@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")

public class User {
	
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY            
    )
    
    @Column(name = "id", updatable = false)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "lastname")
    private String lastname;
   
	@Size(max = 20)
	@Column(name = "username")
	private String username;

    @Column(name = "email")
    private String email;

    @ToString.Exclude
    @Column(name = "password", columnDefinition = "text")
    private String password;
    
    @Column
    private boolean deleted = Boolean.FALSE;
    
    @ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
   
	public User(String name, String lastname, String username, String email,
			 String password) {
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.email = email;		
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setFirst_name(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLast_name(String lastname) {
		this.lastname = lastname;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		this.roles.add(role);
	}
	
    
}
