package com.itsl.repository;

import org.springframework.stereotype.Service;

@Service
public interface ScoresRepository {
	Long   getIdAlumno();
	String getNumControl();
	String getNombre();
	String getCarrera(); 
	String getCreditos(); 
}
