package com.itsl.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itsl.entity.Alumno;
import com.itsl.repository.AlumnosRepository;
import com.itsl.repository.StudentWithCareer;
import com.itsl.springSecurity.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class AlumnosController {
	@Autowired
    private AlumnosRepository alumnosRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	
	// Este metodo sirve para listar todos los `alumnos`
	@GetMapping("/all")
	public List<Alumno> listarTodosLosAlumnos() {
		return alumnosRepository.listAll();		
	}
	
	// Este metodo sirve para guardar un `alumno`
	@PostMapping("/save")
    public ResponseEntity<String> addNewStudent(@RequestBody Alumno alumno) throws Exception {
		
//		System.out.println("alumno: "+alumno);
//		System.out.println("alumno.getNumControl(): "+alumno.getNumControl());
//		System.out.println("alumno.getCarrera().getId(): "+alumno.getCarrera().getId());
		
		alumnosRepository.save(alumno);
		
		Long id = alumnosRepository.getMaxId();
		alumnosRepository.insertNewUser("nada"+id+"@hotmail.com", alumno.getNombre(), alumno.getNombre(), alumno.getNumControl(), encoder.encode(alumno.getNumControl()));
		alumnosRepository.insertNewUserRole();
		
        return ResponseEntity.ok("Student registered successfully!!!");           
    }

	// este metodo sirve para buscar un alumno por `ID`
	@GetMapping("/studentWithCareer/{id}")
	public StudentWithCareer getStudentWithCareer(@PathVariable Long id){				
		return alumnosRepository.studentWithCareer(id);
	}
	
	// este metodo sirve para buscar un alumno por `ID`
	@GetMapping("/student/{id}")
	public ResponseEntity<Alumno> getStudentById(@PathVariable Long id){
		Alumno alumno = alumnosRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el alumno: "+ id+", sorry!!!"));
		
		return ResponseEntity.ok(alumno);
	}
	
	//este metodo sirve para actualizar un `alumno`
	@PutMapping("/student/{id}")
	public ResponseEntity<Alumno> actualizarStudent(@PathVariable Long id, @RequestBody Alumno detallesAlumno){
		Alumno alumno = alumnosRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el alumno con el ID : " + id));
		
		alumno.setNombre(      detallesAlumno.getNombre()     );
		alumno.setNumControl(  detallesAlumno.getNumControl() );
		alumno.setEstado(      detallesAlumno.getEstado()     );
		alumno.setComentarios( detallesAlumno.getEstado()     );
		alumno.setIdCarrera(   detallesAlumno.getIdCarrera()  );		
		
		Alumno alumnoActualizado = alumnosRepository.save(alumno);
		return ResponseEntity.ok(alumnoActualizado);
	}
	
	// Este metodo sirve para listar todas los `alumnos` por `nombre`
	@GetMapping("/studentByName/{name}")
	public List<Alumno> listarAlumnosPorNombre(@PathVariable String name) {
		return alumnosRepository.findByNombre(name);
	}
	
	// Este metodo sirve para obtener el `id` del `alumno` dado el `nombre`
	@GetMapping("/getStudentIDByName/{name}")
	public Long getStudentIDByName(@PathVariable String name) {
		
		Long id = alumnosRepository.getStudentIDByName(name);
		
		return id;
	}
	
	// Este metodo sirve para obtener el `id` del `alumno` dado el `nombre`
	@GetMapping("/getStudentIDByNoCtrl/{noCtrl}")
	public Long getStudentIDByNoCtrl(@PathVariable String noCtrl) {
		
		Long id = alumnosRepository.getStudentIDByNoCtrl(noCtrl);
		
		return id;
	}
	
	// Este metodo sirve para saber si el `alumno` esta `creada`
	@GetMapping("/getNumCtrl/{numCtrl}")
	public Boolean existsByName(@PathVariable String numCtrl) {	
		System.out.println("numCtrl: "+numCtrl);
//		return alumnosRepository.existsByNumControl(numCtrl);
		return !alumnosRepository.isTakenNumCtrl(numCtrl).isEmpty();				
	}
	
	// Este metodo sirve para hacer un `Soft Delete` del `alumno`	
	@RequestMapping(value="/remove/{id}", method = {RequestMethod.DELETE} )
	public void remove(@PathVariable Long id){
		alumnosRepository.deleteById(id);
    }
}
