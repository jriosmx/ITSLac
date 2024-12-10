package com.itsl.repository;

import org.springframework.stereotype.Service;

@Service
public interface StudentWithCareer {
	Long   getId(); 
	String getNombre();
	String getNumControl(); 
	String getEstado(); 	
	String getComentarios(); 
	String getCarrera(); 
}
