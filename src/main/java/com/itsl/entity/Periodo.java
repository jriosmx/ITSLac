package com.itsl.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@Table(name = "periodos")
@SQLDelete(sql = "UPDATE periodos SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Periodo {
	
	// atributos
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name="numPeriodo")
	private int numPeriodo;
	
	@NotNull
	@Column(name="anio")
	private int anio;
	
	@Column
    private boolean deleted = Boolean.FALSE;
	/*****************************************************************/
//	@OneToOne(mappedBy = "periodo")
//    private Credito credito;
	
	@OneToMany(mappedBy = "periodo")
    private Set<Credito> creditos = new HashSet<>();
	/*****************************************************************/
	public Periodo() {
		
	}
	
	

	/**
	 * @param numPeriodo
	 * @param anio
	 * @param deleted
	 * @param creditos
	 */
	public Periodo(@NotNull int numPeriodo, @NotNull int anio, boolean deleted, Set<Credito> creditos) {
		this.numPeriodo = numPeriodo;
		this.anio = anio;
		this.deleted = deleted;
		this.creditos = creditos;
	}



	/**
	 * @return the numPeriodo
	 */
	public int getNumPeriodo() {
		return numPeriodo;
	}

	/**
	 * @param numPeriodo the numPeriodo to set
	 */
	public void setNumPeriodo(int numPeriodo) {
		this.numPeriodo = numPeriodo;
	}

	/**
	 * @return the anio
	 */
	public int getAnio() {
		return anio;
	}

	/**
	 * @param anio the anio to set
	 */
	public void setAnio(int anio) {
		this.anio = anio;
	}



	/**
	 * @return the creditos
	 */
	public Set<Credito> getCreditos() {
		return creditos;
	}



	/**
	 * @param creditos the creditos to set
	 */
	public void setCreditos(Set<Credito> creditos) {
		this.creditos = creditos;
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
	
	

}
