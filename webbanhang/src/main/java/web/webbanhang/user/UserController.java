package web.webbanhang.user;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	// Hien thi thong tin user thông qua e mail
	@GetMapping("/user/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
		try {
			//Tìm user thong qua email
			User user = userRepository.findByEmail(email);

			//Check xem user co ton tai khong
			if (user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}

			//Tra ve thong tin user
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			System.err.println("Lỗi khi lấy thông tin người dùng: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@RequestParam String email, @RequestParam String password) {
		try {
			User user = userRepository.findByEmail(email);

			//Check user co ton tai hay ko
			if (user == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			}

			if (!user.getPassword().equals(password)) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			}

			return ResponseEntity.ok(user);
		} catch (Exception e) {
			System.err.println("Lỗi khi đăng nhập: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
