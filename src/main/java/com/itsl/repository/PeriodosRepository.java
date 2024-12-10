package com.itsl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.itsl.entity.Periodo;

@Service
public interface PeriodosRepository extends JpaRepository<Periodo, Long>{
	
	Boolean existsByAnio(int nombre);

	@Query(value = "SELECT * FROM periodos WHERE anio = :anio \n "
		+ " AND \n "
			+ "	periodos.deleted   = 0" ,nativeQuery = true)
	List<Periodo> findByAnio(@Param("anio") int anio);

	
	@Query(value = "SELECT * FROM periodos WHERE anio LIKE CONCAT('%',:anio,'%')  \n "
			+ " AND \n "
				+ "	periodos.deleted   = 0 \n"
				+ " GROUP BY anio DESC" ,nativeQuery = true)
	List<Periodo> getPeriodosPorAnio(@Param("anio") String anio);

	
	@Query(value = "SELECT id FROM periodos WHERE anio = :anio \n "
		+ " AND \n "
			+ " numPeriodo = :numPeriodo \n "
		+ " AND \n "
			+ "	periodos.deleted   = 0" ,nativeQuery = true)
	Long getPeriodIDByYear(@Param("anio") String anio, @Param("numPeriodo") String numPeriodo);
	
	@Query(value = "SELECT \n"
			+ " count(period.id) > 0 \n"
		+ " FROM \n "
			+ " periodos as period \n"
		+ " WHERE \n"
			+ " period.anio = :anio \n"
		+ " AND \n"
			+ " period.numPeriodo = :periodo \n"
		+ " AND \n"
			+ "	period.deleted   = 0" ,nativeQuery = true)
	int existsPeriod(@Param("anio") String anio, @Param("periodo") String periodo);
	
	@Query(value = "SELECT \n"
			+ "	COUNT(creditos.idPeriodo) > 0\n"
			+ "FROM \n"
			+ "	creditos       \n"
			+ "WHERE\n"
			+ "	creditos.idAlumno = :idAlumno\n"
			+ "AND\n"
			+ "	creditos.idPeriodo = (SELECT periodos.id FROM periodos WHERE periodos.anio = :anio AND periodos.numPeriodo = :periodo AND periodos.deleted=0)" ,nativeQuery = true)
	int existsPeriodInScore(@Param("anio") String anio, @Param("periodo") String periodo, @Param("idAlumno") String idAlumno);
	
	
}