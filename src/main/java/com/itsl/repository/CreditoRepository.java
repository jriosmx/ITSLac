package com.itsl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.itsl.entity.Credito;

@Service
public interface CreditoRepository extends JpaRepository<Credito, Long>{

//	Boolean existsByIdAlumno(Long id);
	
	@Query(value = "SELECT creditos.idAlumno, alumnos.numControl, alumnos.nombre, carreras.nombre as carrera, SUM(creditos.creditos) as creditos \n "
			+ "FROM \n"
			+ "    alumnos, carreras, creditos, periodos \n"
			+ "WHERE \n"
			+ "    creditos.idAlumno  = alumnos.id \n"
			+ "AND \n "
			+ "    creditos.idPeriodo = periodos.id \n"
			+ "AND \n"
			+ "    alumnos.idCarrera = carreras.id \n"
			+ "AND \n"
			+ "	   alumnos.deleted   = 0 \n"
			+ "AND \n"
			+ "    creditos.deleted   = 0 \n"
			+ "AND \n"
			+ "    periodos.deleted   = 0 \n"
			+ "AND \n"
			+ "    carreras.deleted  = 0 GROUP BY alumnos.nombre" ,nativeQuery = true)
	List<ScoresRepository> all();
	
	
	@Query(value = "SELECT "
	+ 		"creditos.id as idCredito, creditos.idAlumno, creditos.comentarios, creditos.creditos as creditoPer, alumnos.numControl, alumnos.nombre, alumnos.estado, carreras.nombre as carrera, periodos.anio, periodos.numPeriodo, "
	+ "      ( "
	+ "     	SELECT SUM(creditos.creditos) as creditos "
	+ "        	FROM "
	+ "            creditos "
	+ "       	 WHERE "
	+ "            creditos.idAlumno  = :idAlumno "
	+ "        	AND "
	+ "            creditos.deleted   = 0 "
	+ "    	 ) as creditosTot "
	+ "FROM "
	+		"creditos, alumnos,carreras, periodos "
	+ "WHERE "
	+		"creditos.idAlumno  = :idAlumno "
	+ "AND "
	+		"creditos.idAlumno = alumnos.id "
    + "AND "
	+		"alumnos.idCarrera = carreras.id "
	+ "AND "
	+		"creditos.idPeriodo = periodos.id "	
    + "AND "
	+		"alumnos.deleted   = 0 " 
	+ "AND " 
	+		"creditos.deleted  = 0 " 
	+ "AND " 
	+		"periodos.deleted  = 0 " 
	+ "AND "
	+		"carreras.deleted  = 0 ", nativeQuery = true)
	List<PeriodsAndScoresReporsitory> getPeriods(@Param("idAlumno") Long idAlumno);
	
	@Query(value = "SELECT "
			+ 		"creditos.id as idCredito, creditos.idAlumno, creditos.comentarios, creditos.creditos as creditoPer, alumnos.numControl, alumnos.nombre, alumnos.estado, carreras.nombre as carrera, periodos.anio, periodos.numPeriodo, "
			+       "( "
			+ "     	SELECT SUM(creditos.creditos) as creditos "
			+ "        	FROM "
			+ "            creditos "
			+ "       	 WHERE "
			+ "            creditos.idAlumno  = (SELECT creditos.idAlumno WHERE creditos.id = :idCredito) "
			+ "        	AND "
			+ "            creditos.deleted   = 0 "
			+ "    	 ) as creditosTot "
			+ "FROM "
			+		"creditos, alumnos,carreras, periodos "
			+ "WHERE "
			+		"creditos.id       = :idCredito "
			+ "AND "
			+		"creditos.idAlumno = alumnos.id "
			+ "AND "
			+		"alumnos.idCarrera = carreras.id "
			+ "AND "
			+		"creditos.idPeriodo = periodos.id "	
			+ "AND "
			+		"alumnos.deleted   = 0 " 
			+ "AND " 
			+		"creditos.deleted  = 0 " 
			+ "AND " 
			+		"periodos.deleted  = 0 " 
			+ "AND "
			+		"carreras.deleted  = 0 ", nativeQuery = true)
	PeriodsAndScoresReporsitory getScore(@Param("idCredito") Long idCredito);
	
	@Query(value = "SELECT * FROM creditos WHERE idAlumno LIKE CONCAT('%',:id,'%') \n "
			+ " AND \n "
			+ "	creditos.deleted   = 0" ,nativeQuery = true)
	List<Credito> findByIdAlumno(@Param("id") Long id);
	
	@Query(value = "SELECT idAlumno FROM creditos WHERE idAlumno = :id \n "
			+ " AND \n "
			+ "	creditos.deleted   = 0" ,nativeQuery = true)
	Long getCreditoIDByIdAlumno(@Param("id") Long id);
}
