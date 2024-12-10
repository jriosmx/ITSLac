package com.itsl.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "creditos")
@SQLDelete(sql = "UPDATE creditos SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Credito {

	// atributos
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name="creditos",  scale = 2)
	private double creditos;
		
	@NotNull
	@Column(name="comentarios", length = 300)
	private String comentarios;
	
	@Column(name="idPeriodo")
	private Long idPeriodo;
	
	@Column(name="idAlumno")
	private Long idAlumno;
	
	@Column
    private boolean deleted = Boolean.FALSE;
    /*****************************************************************/	
	@ManyToOne
    @JoinColumn(name = "idPeriodo", referencedColumnName = "id", insertable=false, updatable=false,nullable = false, 
    			foreignKey = @ForeignKey(name="fk_periodo_id"))
	@JsonIgnore
    private Periodo periodo;
	
	@ManyToOne
	@JoinColumn(name = "idAlumno", referencedColumnName = "id", insertable=false, updatable=false,nullable = false,
				foreignKey = @ForeignKey(name="fk_alumno_id"))
	@JsonIgnore
	private Alumno alumno;
    /*****************************************************************/
	
	public Credito() {
		
	}

	/**
	 * @param creditos
	 * @param comentarios
	 * @param idPeriodo
	 * @param idAlumno
	 * @param deleted
	 * @param periodo
	 * @param alumno
	 */
	public Credito(@NotNull double creditos, @NotNull String comentarios, Long idPeriodo, Long idAlumno,
			boolean deleted, Periodo periodo, Alumno alumno) {
		this.creditos = creditos;
		this.comentarios = comentarios;
		this.idPeriodo = idPeriodo;
		this.idAlumno = idAlumno;
		this.deleted = deleted;
		this.periodo = periodo;
		this.alumno = alumno;
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
	 * @return the creditos
	 */
	public double getCreditos() {
		return creditos;
	}

	/**
	 * @param creditos the creditos to set
	 */
	public void setCreditos(double creditos) {
		this.creditos = creditos;
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

	/**
	 * @return the periodo
	 */
	public Periodo getPeriodo() {
		return periodo;
	}

	/**
	 * @param periodo the periodo to set
	 */
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}


	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}



	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}



	/**
	 * @return the alumno
	 */
	public Alumno getAlumno() {
		return alumno;
	}



	/**
	 * @param alumno the alumno to set
	 */
	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}







	/**
	 * @return the idPeriodo
	 */
	public Long getIdPeriodo() {
		return idPeriodo;
	}







	/**
	 * @param idPeriodo the idPeriodo to set
	 */
	public void setIdPeriodo(Long idPeriodo) {
		this.idPeriodo = idPeriodo;
	}







	/**
	 * @return the idAlumno
	 */
	public Long getIdAlumno() {
		return idAlumno;
	}







	/**
	 * @param idAlumno the idAlumno to set
	 */
	public void setIdAlumno(Long idAlumno) {
		this.idAlumno = idAlumno;
	}
	
	
	
	
}
