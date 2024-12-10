package com.itsl.springSecurity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.itsl.springSecurity.entity.ERole;
import com.itsl.springSecurity.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
	
	@Query(value = "SELECT id FROM roles WHERE id=(SELECT role_id FROM users_roles WHERE user_id:id", nativeQuery = true)
	Long findRolesId(@Param("id") Long id);

	@Query(value = "SELECT role_id FROM user_roles WHERE user_id=:id", nativeQuery = true)
	Long findRoleId(@Param("id") Long id);

	@Query(value = "SELECT role_name FROM roles WHERE id=:id", nativeQuery = true)
	String findRole(@Param("id") Long id);

	@Query(value = "SELECT id FROM users WHERE username LIKE :userName", nativeQuery = true)
	String findId(@Param("userName") String userName);
	
	@Query(value = "SELECT role_id FROM user_roles", nativeQuery = true)
	List<String> findAllRoles();
}
