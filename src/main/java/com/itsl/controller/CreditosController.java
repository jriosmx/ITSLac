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

import com.itsl.entity.Alumno;
import com.itsl.entity.Credito;
import com.itsl.repository.AlumnosRepository;
import com.itsl.repository.CreditoRepository;
import com.itsl.repository.PeriodsAndScoresReporsitory;
import com.itsl.repository.ScoresRepository;
import com.itsl.springSecurity.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scores")
public class CreditosController {
	@Autowired
    private CreditoRepository creditosRepository;
	
	@Autowired
    private AlumnosRepository alumnosRepository;
	
	// Este metodo sirve para listar todos los `creditos`
	@GetMapping("/all")
	public List<ScoresRepository> listarTodasLosCreditos() {
		return creditosRepository.all();		
	}
	
	// Este metodo sirve para guardar un `credito`
	@PostMapping("/save")
    public ResponseEntity<String> addNewCredito(@RequestBody Credito credito) throws Exception {

		creditosRepository.save(credito);
    	
        return ResponseEntity.ok("Score registered successfully!!!");           
    }

	// este metodo sirve para buscar un credito por `ID`
	@GetMapping("/score/{id}")
	public ResponseEntity<Credito> getScoreById(@PathVariable Long id){
		Credito credito = creditosRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el credito: "+ id+", sorry!!!"));
		
		return ResponseEntity.ok(credito);
	}
	
	//este metodo sirve para actualizar los `creditos`
	@PutMapping("/score/{id}")
	public ResponseEntity<String> actualizarCredito(@PathVariable Long id, @RequestBody Credito detallesCredito){
		Credito credito = creditosRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el credito con el ID : " + id));
		
		credito.setComentarios( detallesCredito.getComentarios() );
		credito.setCreditos(    detallesCredito.getCreditos()    );
		credito.setPeriodo(     detallesCredito.getPeriodo()     );		
		
		
		Credito creditoActualizado = creditosRepository.save(credito);
		return ResponseEntity.ok("Score updated successfully!!!");
	}
	
	// Este metodo sirve para listar todos las `creditos` por `nombre`
	@GetMapping("/scoresByName/{name}")
	public List<Credito> listarCreditoPorNombre(@PathVariable Long id) {
		return creditosRepository.findByIdAlumno(id);
	}
	
	// Este metodo sirve para obtener el `id` de la `credito` dado el `nombre`
	@GetMapping("/getScoreIDByName/{id}")
	public Long getCreditoIDByName(@PathVariable Long id) {
		
		Long idAlumno = creditosRepository.getCreditoIDByIdAlumno( id );
		
		return idAlumno;
	}
	
	// Este metodo sirve para saber si el `credito` esta `creado`
	@GetMapping("/getNumCtrl/{numCtrl}")
	public List<Alumno> getNumCtrl(@PathVariable String numCtrl) {		
		return alumnosRepository.getNumCtrl(numCtrl);			
	}
	
	// Este metodo sirve para obtener todos los `periodos` del alumno
	@GetMapping("/getPeriods/{idAlumno}")
	public List<PeriodsAndScoresReporsitory> getNumCtrl(@PathVariable Long idAlumno) {		
		return creditosRepository.getPeriods(idAlumno);			
	}
	
	// Este metodo sirve para obtener el `credito` del alumno
	@GetMapping("/getScore/{idCredito}")
	public PeriodsAndScoresReporsitory getCredito(@PathVariable Long idCredito) {		
		return creditosRepository.getScore(idCredito);			
	}
	
	// Este metodo sirve para hacer un `Soft Delete` de la `credito`	
	@RequestMapping(value="/remove/{id}", method = {RequestMethod.DELETE} )
	public void remove(@PathVariable Long id){
		creditosRepository.deleteById(id);
    }
}
