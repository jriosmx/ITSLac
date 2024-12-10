package com.itsl.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "alumnos")
@SQLDelete(sql = "UPDATE alumnos SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Alumno {
	
	// atributos
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "nombre", length = 150)
	private String nombre;
	
	@NotNull
	@Column(name = "numControl", length = 8)
	private String numControl;
	
	@NotNull
	@Column(name = "estado", length = 50)
	private String estado;
	
	@NotNull
	@Column(name="comentarios")
	private String comentarios;
	
	@Column
    private boolean deleted = Boolean.FALSE;
	
	@NotNull
    @Column(name="idCarrera")
    private Long idCarrera;
	
	/*****************************************************************/
	@OneToOne
    @JoinColumn(name = "idCarrera", nullable=false, insertable=false, updatable=false, 
    			referencedColumnName = "id", 
    			foreignKey = @ForeignKey(name="fk_carrera_id"))
    private Carrera carrera;
	
	@OneToMany(mappedBy = "alumno")
    private Set<Credito> creditos = new HashSet<>();
	/*****************************************************************/
	
	//constructores
	public Alumno() {
		
	}

	/**
	 * @param nombre
	 * @param numControl
	 * @param estado
	 * @param comentarios
	 * @param idCarrera
	 * @param carrera
	 * @param creditos
	 */
	public Alumno(@NotNull String nombre, @NotNull String numControl, @NotNull String estado,
			@NotNull String comentarios, @NotNull Long idCarrera, Set<Credito> creditos) {
		this.nombre = nombre;
		this.numControl = numControl;
		this.estado = estado;
		this.comentarios = comentarios;
		this.idCarrera = idCarrera;
		this.creditos = creditos;
	}

	// getters & setters

	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @return the idCarrera
	 */
	public Long getIdCarrera() {
		return idCarrera;
	}

	/**
	 * @param idCarrera the idCarrera to set
	 */
	public void setIdCarrera(Long idCarrera) {
		this.idCarrera = idCarrera;
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


	/**
	 * @return the numControl
	 */
	public String getNumControl() {
		return numControl;
	}


	/**
	 * @param numControl the numControl to set
	 */
	public void setNumControl(String numControl) {
		this.numControl = numControl;
	}


	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}


	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}


	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}


	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	
	
}
