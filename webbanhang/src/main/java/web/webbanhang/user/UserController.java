package web.webbanhang.user;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import web.webbanhang.jpa.RoleJpa;
import web.webbanhang.jpa.UserJpa;
import web.webbanhang.role.Role;

@RestController
public class UserController {
	private UserJpa userRepository;	
	private RoleJpa roleRepository; 
	
	
	public UserController(UserJpa userRepository, RoleJpa roleRepository) {
		
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> retrieveAllUser() {
	    try {
	        List<User> users = userRepository.findAll();
	        return ResponseEntity.ok(users);
	    } catch (Exception e) {
	        System.err.println("Error retrieving users: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
        if (user.getRole() == null) {
            System.out.println("Loi 1");
        }


        Optional<Role> existingRole = roleRepository.findById(user.getRole().getRole()+1);
        Role role = existingRole.get();

        if (role == null) {
            
        	System.out.println("Loi 2");
        }
        user.setRole(role);
		User addedUser = userRepository.save(user);
		
		return ResponseEntity.noContent().build();
	}
}
