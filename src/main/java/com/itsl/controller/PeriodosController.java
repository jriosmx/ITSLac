package com.itsl.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itsl.entity.Periodo;
import com.itsl.repository.PeriodosRepository;
import com.itsl.springSecurity.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/periods")
public class PeriodosController {
	@Autowired
    private PeriodosRepository periodosRepository;
	
	// Este metodo sirve para listar todos las `periodos`
	@GetMapping("/all")
	public List<Periodo> listarTodasLosPeriodos() {
		return periodosRepository.findAll();		
	}
	
	// Este metodo sirve para guardar un `periodos`
	@PostMapping("/save")
    public ResponseEntity<String> addNewPeriodo(@RequestBody Periodo periodo) throws Exception {

		periodosRepository.save(periodo);
    	
        return ResponseEntity.ok("Periodo registered successfully!!!");           
    }

	// este metodo sirve para buscar un periodo por `ID`
	@GetMapping("/periods/{id}")
	public ResponseEntity<Periodo> getPeriodById(@PathVariable Long id){
		Periodo periodo = periodosRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el periodo: "+ id+", sorry!!!"));
		
		return ResponseEntity.ok(periodo);
	}
	
	//este metodo sirve para actualizar un `periodo`
	@PutMapping("/periods/{id}")
	public ResponseEntity<Periodo> actualizarEmpleado(@PathVariable Long id, @RequestBody Periodo detallesPeriodo){
		Periodo periodo = periodosRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el periodo con el ID : " + id));
		
		periodo.setAnio( detallesPeriodo.getAnio() );
		periodo.setNumPeriodo( detallesPeriodo.getNumPeriodo() );
		
		Periodo periodoActualizado = periodosRepository.save(periodo);
		return ResponseEntity.ok(periodoActualizado);
	}
	
	// Este metodo sirve para listar todos los `periodos` por `año`
	@GetMapping("/periodsByYear/{anio}")
	public List<Periodo> listarPeriodosPorAnio(@PathVariable String anio) {
		return periodosRepository.getPeriodosPorAnio(anio);
	}
	
	// Este metodo sirve para listar todos los `periodos` por `año`
	@GetMapping("/periodByYear/{anio}")
	public List<Periodo> listarPeriodoPorAnio(@PathVariable int anio) {
		return periodosRepository.findByAnio(anio);
	}
	
	// Este metodo sirve para obtener el `id` del `periodo` dado el `año`
	@RequestMapping(path = "/getPeriodIDByYear", method = RequestMethod.GET) 
	public Long getCarreraIDByName(@RequestParam Map<String, String> customQuery) {
		
//		System.out.println(
//        " anio: "+customQuery.get("anio")+ 
//        " periodo: "+customQuery.get("numPeriodo") );

//		System.out.println("customQuery: "+customQuery.toString());
		
		Long id = periodosRepository.getPeriodIDByYear(customQuery.get("anio"), customQuery.get("numPeriodo"));
		
		return id;
	}
	
	// Este metodo sirve para saber si el `periodo` esta `creado`	    DOS PARAMETROS GET
	@RequestMapping(path = "/getPeriod", method = RequestMethod.GET) 
	public int existsByYearAndPeriod(@RequestParam Map<String, String> customQuery) {	
//		System.out.println("id: "+customQuery.get("id")+
//				        " anio: "+customQuery.get("anio")+ 
//				        " periodo: "+customQuery.get("numPeriodo"));
//		
//		System.out.println("customQuery: "+customQuery.toString());
				
		return periodosRepository.existsPeriod(customQuery.get("anio"), customQuery.get("numPeriodo"));				
	}
	
	// Este metodo sirve para saber si el `periodo` esta `creado`	    TRES PARAMETROS GET
	@RequestMapping(path = "/existsPeriodInScore", method = RequestMethod.GET) 
	public int existsByYearAndPeriodInScore(@RequestParam Map<String, String> customQuery) {	
//			System.out.println("id: "+customQuery.get("id")+
//					        " anio: "+customQuery.get("anio")+ 
//					        " periodo: "+customQuery.get("numPeriodo"));
//			
//			System.out.println("customQuery: "+customQuery.toString());
		
		return periodosRepository.existsPeriodInScore(customQuery.get("anio"), customQuery.get("numPeriodo"),  customQuery.get("idAlumno"));				
	}
	
	// Este metodo sirve para hacer un `Soft Delete` del `periodo`	
	@RequestMapping(value="/remove/{id}", method = {RequestMethod.DELETE} )
	public void remove(@PathVariable Long id){
		periodosRepository.deleteById(id);
    }
}
