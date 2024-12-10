package com.itsl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.itsl.entity.Alumno;

import jakarta.transaction.Transactional;

@Service
public interface AlumnosRepository extends JpaRepository<Alumno, Long>{
	
	@Query(value = "SELECT MAX(id) FROM alumnos WHERE alumnos.deleted = 0", nativeQuery = true)
	Long getMaxId();
	
	@Query(value = "SELECT * FROM alumnos WHERE alumnos.deleted = 0", nativeQuery = true)
	List<Alumno> listAll();
	
	@Modifying
	@Query(value = "INSERT INTO users(deleted, email, lastname, name, password, username) VALUES(0,:email,:lastname,:name,:password,:username)", nativeQuery = true)
	@Transactional
	void insertNewUser(@Param("email") String email, @Param("lastname") String lastname, @Param("name") String name, @Param("username") String username, @Param("password") String password);
	
	@Modifying
	@Query(value = "INSERT INTO user_roles(user_id, role_id) VALUES( (SELECT MAX(id) FROM users WHERE users.deleted = 0), 1 )", nativeQuery = true)
	@Transactional
	void insertNewUserRole();

	
	//	Boolean existsByNumControl(String nombre);

	@Query(value="SELECT numControl FROM alumnos WHERE numControl = :numCtrl \n"
				+ " AND \n"
				+ " alumnos.deleted = 0", nativeQuery = true)
	List<String> isTakenNumCtrl(@Param("numCtrl") String numCtrl);

	@Query(value = "SELECT * FROM alumnos WHERE nombre LIKE CONCAT('%',:name,'%') \n" 
			+ " AND \n"
			+ "     alumnos.deleted = 0", nativeQuery = true)
	List<Alumno> findByNombre(@Param("name") String name);
	
	@Query(value = "SELECT id FROM alumnos WHERE nombre LIKE :nombre \n" 
			+ " AND \n"
			+ "     alumnos.deleted = 0", nativeQuery = true)
	Long getStudentIDByName(@Param("nombre") String nombre);
	
	@Query(value = "SELECT id FROM alumnos WHERE numControl = :noCtrl \n" 
			+ " AND \n"
			+ "     alumnos.deleted = 0", nativeQuery = true)
	Long getStudentIDByNoCtrl(@Param("noCtrl") String noCtrl);
	
	@Query(value = 
			  " SELECT \n"
			+ "		alumnos.id, alumnos.nombre, alumnos.numControl, alumnos.estado, alumnos.comentarios, carreras.nombre as carrera \n"			
			+ "	FROM \n"
			+ "		alumnos, carreras \n"
			+ "	WHERE \n"
			+ "     alumnos.id = :id \n"
			+ " AND \n"
			+ "     alumnos.idCarrera = carreras.id \n"
			+ " AND \n"
			+ "     carreras.deleted = 0", nativeQuery = true)
	StudentWithCareer studentWithCareer(@Param("id") Long id);
	
	@Query(value="SELECT * FROM alumnos WHERE numControl like CONCAT('%',:numCtrl,'%') \n"
			+ " AND \n"
			+ " alumnos.deleted = 0", nativeQuery = true)
	List<Alumno> getNumCtrl(@Param("numCtrl") String numCtrl);
	
	
	
}
