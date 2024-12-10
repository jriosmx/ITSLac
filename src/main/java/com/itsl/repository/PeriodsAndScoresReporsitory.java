package com.itsl.repository;

import org.springframework.stereotype.Service;

@Service
public interface PeriodsAndScoresReporsitory {
	Long   getIdAlumno(); 
	String getComentarios();
	float  getCreditoPer(); 
	Long   getIdCredito();
	String getNumControl(); 	
	String getNombre(); 
	String getCarrera();
	String getEstado();
	int    getAnio();
	int    getNumPeriodo();
	float  getCreditosTot();
}
