package com.itsl.springSecurity.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itsl.springSecurity.entity.ERole;
import com.itsl.springSecurity.entity.Role;
import com.itsl.springSecurity.entity.User;
import com.itsl.springSecurity.exceptions.ResourceNotFoundException;
import com.itsl.springSecurity.payload.request.SignupRequest;
import com.itsl.springSecurity.payload.response.MessageResponse;
import com.itsl.springSecurity.repository.RoleRepository;
import com.itsl.springSecurity.repository.UserRepository;
import com.itsl.springSecurity.repository.UserWithRoles;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
    UserRepository usersRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	// Este metodo sirve para listar todos los `usuarios` con sus `Roles`
	@GetMapping("/allUsersWithRoles")
	public List<UserWithRoles> listarTodosLosUsuariosConRoles() {
		return usersRepository.findAllUsersWithRoles();
	}
	
	// este metodo sirve para buscar un `usuario` por el `username`
	@GetMapping("/getUserByUsername/{username}")
	public User getUserByUsername(@PathVariable String username) {
		return usersRepository.findUserByUsername(username);
	}
	
	// este metodo sirve para buscar un `usuario` por `ID`
	@GetMapping("/getUserByID/{id}")
	public ResponseEntity<User> obtenerUserPorId(@PathVariable Long id){
		User user = usersRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el usuario: "+ id+", sorry!!!"));
				
		return ResponseEntity.ok(user);
	}
	
	// Este metodo sirve para listar todos los `usuarios`
	@GetMapping("/all")
	public List<User> listarTodosLosUsuarios() {
		return usersRepository.findAll();
	}
	
	//este metodo sirve para eliminar un `usuario`
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String,Boolean>> eliminarUsuario(@PathVariable Long id){
		User user = usersRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el `usuario` con el ID : " + id));
		
		this.usersRepository.delete(user);
		
		Map<String, Boolean> respuesta = new HashMap<>();
		respuesta.put("Eliminado",Boolean.TRUE);
		return ResponseEntity.ok(respuesta);
	}
			
	//este metodo sirve para `actualizar` un `usuario`
	@PutMapping("/update/{id}")
	public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody SignupRequest signUpRequest){
		User user = usersRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el Usuario con el ID : " + id));				
		
		user.setFirst_name( signUpRequest.getName() );
		user.setLast_name(  signUpRequest.getLastname()  );
		user.setEmail(      signUpRequest.getEmail()      );
		user.setUsername(   signUpRequest.getUsername()   );				
		
		System.out.println("<----- detallesUsuario.getRoles(): "+signUpRequest.getRole() );
		
		String strRol = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRol == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found. 1"));
			roles.add(userRole);
		} else {
			
				switch (strRol) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found. 2"));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found. 3"));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found. 4"));
					roles.add(userRole);
				}
			
		}

		user.setRoles(roles);
		usersRepository.save(user);
		
		return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
	}
	
	// este metodo sirve para ver si existe un correo electronico
	@GetMapping("/getEmail/{email}")
	public int obtenerEmail(@PathVariable String email){				
		return this.usersRepository.existsEmail(email);
	}
	
	// este metodo sirve para ver si existe un username
	@GetMapping("/getUsername/{username}")
	public int obtenerUsername(@PathVariable String username){				
		return this.usersRepository.existsUsername(username);
	}
			
	//este metodo sirve para `actualizar` el `password`
	@PutMapping("/updatePasswd/{id}")
	public ResponseEntity<?> actualizaPassword(@PathVariable Long id, @RequestBody String passwd) {
		User user = usersRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el Usuario con el ID : " + id));
		
//				System.out.println("passwd: "+passwd );
		passwd = encoder.encode(passwd);
		usersRepository.updatePasswd(id, passwd);
		
		return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
	}
	
	// Este metodo sirve para hacer un `Soft Delete` del `usuario`	
	@RequestMapping(value="/remove/{id}", method = {RequestMethod.DELETE} )
	public void remove(@PathVariable Long id){
		usersRepository.deleteById(id);
	}

}
