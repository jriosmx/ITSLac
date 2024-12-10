package com.itsl.springSecurity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itsl.springSecurity.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	@Query(value = "SELECT \n"
			+ "	users.id, users.name, users.lastname, users.email, users.username, \n"
			+ "    IF(user_roles.role_id=1, 'Usuario', 'Administrador') as role\n"
			+ "	FROM \n"
			+ "		users, user_roles \n"
			+ "	WHERE \n"
			+ "		users.id = user_roles.user_id", nativeQuery = true)
	List<UserWithRoles> findAllUsersWithRoles();
	
	@Query(value = "SELECT \n"
			+ "	   * \n"
			+ "	FROM \n"
			+ "		users \n"
			+ "	WHERE \n"
			+ "		users.username LIKE :username", nativeQuery = true)
	User findUserByUsername(@Param("username") String username);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE users SET password=:passwd WHERE id=:id", nativeQuery = true)
	public void updatePasswd(@Param("id") Long id, @Param("passwd") String passwd);	
	
	@Query(value = "SELECT \n"
			+ " count(user.id) > 0 \n"
		+ " FROM \n "
			+ " users as user \n"
		+ " WHERE \n"
			+ " user.email = :email \n"		
		+ " AND \n"
			+ "	user.deleted   = 0" ,nativeQuery = true)
	int existsEmail(@Param("email") String email);
	
	@Query(value = "SELECT \n"
			+ " count(user.id) > 0 \n"
		+ " FROM \n "
			+ " users as user \n"
		+ " WHERE \n"
			+ " user.username = :username \n"		
		+ " AND \n"
			+ "	user.deleted   = 0" ,nativeQuery = true)
	int existsUsername(@Param("username") String username);
}
