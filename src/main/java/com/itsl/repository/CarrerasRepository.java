package com.itsl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.itsl.entity.Carrera;

@Service
public interface CarrerasRepository extends JpaRepository<Carrera, Long>{
	
	Boolean existsByNombre(String nombre);

	@Query(value = "SELECT * FROM carreras WHERE nombre LIKE CONCAT('%',:name,'%') \n "
			+ " AND \n "
			+ "	carreras.deleted   = 0" ,nativeQuery = true)
	List<Carrera> findByNombre(@Param("name") String name);
	
	@Query(value = "SELECT id FROM carreras WHERE nombre LIKE :carrera_nombre \n "
			+ " AND \n "
			+ "	carreras.deleted   = 0" ,nativeQuery = true)
	Long getCarreraIDByName(@Param("carrera_nombre") String carrera_nombre);
}
