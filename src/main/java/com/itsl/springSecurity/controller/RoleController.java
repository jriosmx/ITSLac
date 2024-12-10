package com.itsl.springSecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itsl.springSecurity.repository.RoleRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/role")
public class RoleController {
	@Autowired
	RoleRepository roleRepository;
	
	// Este metodo sirve para obtener el `nombre` del `Role` de un `usuario`
	@GetMapping("/getRole/{id}")
	public Long obtenerRole(@PathVariable Long id) {		
		Long rolesId = roleRepository.findRoleId(id);
		
		return rolesId;
	}
	
	// Este metodo sirve para obtener todos los `Roles`
	@GetMapping("/all")
	public List<String> obtenerRoles() {
		return roleRepository.findAllRoles();
	}
	
	// Este metodo sirve para obtener el `id` del `usuario`
	@GetMapping("/getId/{userName}")
	public String obtenerId(@PathVariable String userName) {
		return roleRepository.findId(userName);
	}

}
