package com.itsl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itsl.entity.Carrera;
import com.itsl.repository.CarrerasRepository;
import com.itsl.springSecurity.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/careers")
public class CarrerasController {
	@Autowired
    private CarrerasRepository carrerasRepository;
	
	// Este metodo sirve para listar todos las `carreras`
	@GetMapping("/all")
	public List<Carrera> listarTodasLasCarreras() {
		return carrerasRepository.findAll();		
	}
	
	// Este metodo sirve para guardar un `carreras`
	@PostMapping("/save")
    public ResponseEntity<String> addNewCarrera(@RequestBody Carrera carrera) throws Exception {

		carrerasRepository.save(carrera);
    	
        return ResponseEntity.ok("Carrera registered successfully!!!");           
    }

	// este metodo sirve para buscar un cliente por `ID`
	@GetMapping("/careers/{id}")
	public ResponseEntity<Carrera> getCustomerById(@PathVariable Long id){
		Carrera carrera = carrerasRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe la carrera: "+ id+", sorry!!!"));
		
		return ResponseEntity.ok(carrera);
	}
	
	//este metodo sirve para actualizar `carreras`
	@PutMapping("/careers/{id}")
	public ResponseEntity<Carrera> actualizarEmpleado(@PathVariable Long id, @RequestBody Carrera detallesCarrera){
		Carrera carrera = carrerasRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el carrera con el ID : " + id));
		
		carrera.setNombre(detallesCarrera.getNombre());
		
		
		Carrera carreraActualizado = carrerasRepository.save(carrera);
		return ResponseEntity.ok(carreraActualizado);
	}
	
	// Este metodo sirve para listar todas las `carreras` por `nombre`
	@GetMapping("/careerByName/{name}")
	public List<Carrera> listarCarreraPorNombre(@PathVariable String name) {
		return carrerasRepository.findByNombre(name);
	}
	
	// Este metodo sirve para obtener el `id` de la `carrera` dado el `nombre`
	@GetMapping("/getCareerIDByName/{name}")
	public Long getCarreraIDByName(@PathVariable String name) {
		
		Long id = carrerasRepository.getCarreraIDByName(name);
		
		return id;
	}
	
	// Este metodo sirve para saber si la `carrera` esta `creada`
	@GetMapping("/getCareer/{name}")
	public Boolean existsByName(@PathVariable String name) {		
		return carrerasRepository.existsByNombre(name);				
	}
	
	// Este metodo sirve para hacer un `Soft Delete` de la `carrera`	
	@RequestMapping(value="/remove/{id}", method = {RequestMethod.DELETE} )
	public void remove(@PathVariable Long id){
		carrerasRepository.deleteById(id);
    }
}
