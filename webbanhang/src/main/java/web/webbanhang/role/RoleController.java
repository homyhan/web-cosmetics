package web.webbanhang.role;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import web.webbanhang.jpa.RoleJpa;
import web.webbanhang.user.User;

@RestController
public class RoleController {
	
	private RoleJpa roleRepository;

	public RoleController(RoleJpa roleRepository) {
		super();
		this.roleRepository = roleRepository;
	}

	@PostMapping("/roles")
	public ResponseEntity<Role> createRoles(@RequestBody Role role) {
		roleRepository.save(role);
		return ResponseEntity.noContent().build();
	}
	@GetMapping("/roles")
	public List<Role> retrieveAllUser(){
		return roleRepository.findAll();
	}
	@GetMapping("/roles/{id}")
	public Role getRoleById(@PathVariable int id){
		Role role = roleRepository.findById(id).get();
		return role;
	}
}