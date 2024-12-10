package com.itsl.entity;

import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "carreras")
@SQLDelete(sql = "UPDATE carreras SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Carrera {
	
	// atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@NotNull
	@Column(name = "nombre", length = 150)
	private String nombre;
	
	@Column
    private boolean deleted = Boolean.FALSE;
	
//	@OneToOne(mappedBy = "carrera")
//    private Alumno alumno;
	
	@OneToMany(mappedBy="carrera")
	@JsonIgnore
    private Set<Alumno> alumnos;
	
	public Carrera() {
		
	}

	/**
	 * @param id
	 * @param nombre
	 */
	public Carrera(Long id, @NotNull String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
