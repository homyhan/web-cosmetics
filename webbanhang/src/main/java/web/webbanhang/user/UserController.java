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
//	@GetMapping("/users/search/{keyword}")
//	public ResponseEntity<List<User>> searchUsersByName(@PathVariable String keyword) {
//		try {
//			System.out.println("Searching users by keyword: " + keyword);
//			List<User> users = userRepository.findByFullNameContainingIgnoreCase(keyword);
//
//			if (users.isEmpty()) {
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//			}
//
//			return ResponseEntity.ok(users);
//		} catch (Exception e) {
//			System.err.println("Lỗi khi tìm kiếm user theo tên: " + e.getMessage());
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//		}
//	}

//	@GetMapping("/users/search/{keyword}")
//	public ResponseEntity<List<User>> searchUsersByName(@PathVariable String keyword) {
//		try {
//			// Xử lý chuỗi keyword
//			keyword = keyword.toLowerCase().replaceAll("\\s+", ""); // Chuyển thành chữ thường và loại bỏ khoảng trắng
//			keyword = java.text.Normalizer.normalize(keyword, java.text.Normalizer.Form.NFD)
//					.replaceAll("\\p{M}", ""); // Loại bỏ dấu
//
//			List<User> users = userRepository.findByFullNameContainingIgnoreCase(keyword);
//
//			if (users.isEmpty()) {
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//			}
//
//			return ResponseEntity.ok(users);
//		} catch (Exception e) {
//			System.err.println("Lỗi khi tìm kiếm user theo tên: " + e.getMessage());
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//		}
//	}


	@GetMapping("/users/search/{keyword}")
	public ResponseEntity<List<User>> searchUsersByName(@PathVariable String keyword) {
		try {
			// Chuyển về chữ thường
			keyword = keyword.toLowerCase();

			// Loại bỏ dấu
			keyword = removeAccents(keyword);

			List<User> users = userRepository.findByFullNameContainingIgnoreCase(keyword);

			if (users.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}

			return ResponseEntity.ok(users);
		} catch (Exception e) {
			System.err.println("Lỗi khi tìm kiếm user theo tên: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	// Hàm loại bỏ dấu
	private String removeAccents(String input) {
		String[] accentChars = {"à", "á", "ạ", "ả", "ã", "â", "ầ", "ấ", "ậ", "ẩ", "ẫ", "ă", "ằ", "ắ", "ặ", "ẳ", "ẵ", "è", "é", "ẹ", "ẻ", "ẽ", "ê", "ề", "ế", "ệ", "ể", "ễ", "ì", "í", "ị", "ỉ", "ĩ", "ò", "ó", "ọ", "ỏ", "õ", "ô", "ồ", "ố", "ộ", "ổ", "ỗ", "ơ", "ờ", "ớ", "ợ", "ở", "ỡ", "ù", "ú", "ụ", "ủ", "ũ", "ư", "ừ", "ứ", "ự", "ử", "ữ", "ỳ", "ý", "ỵ", "ỷ", "ỹ", "đ", "À", "Á", "Ạ", "Ả", "Ã", "Â", "Ầ", "Ấ", "Ậ", "Ẩ", "Ẫ", "Ă", "Ằ", "Ắ", "Ặ", "Ẳ", "Ẵ", "È", "É", "Ẹ", "Ẻ", "Ẽ", "Ê", "Ề", "Ế", "Ệ", "Ể", "Ễ", "Ì", "Í", "Ị", "Ỉ", "Ĩ", "Ò", "Ó", "Ọ", "Ỏ", "Õ", "Ô", "Ồ", "Ố", "Ộ", "Ổ", "Ỗ", "Ơ", "Ờ", "Ớ", "Ợ", "Ở", "Ỡ", "Ù", "Ú", "Ụ", "Ủ", "Ũ", "Ư", "Ừ", "Ứ", "Ự", "Ử", "Ữ", "Ỳ", "Ý", "Ỵ", "Ỷ", "Ỹ", "Đ"};
		String[] noAccentChars = {"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "e", "e", "e", "e", "e", "e", "e", "e", "e", "e", "e", "i", "i", "i", "i", "i", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "o", "u", "u", "u", "u", "u", "u", "u", "u", "u", "u", "u", "y", "y", "y", "y", "y", "d", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "I", "I", "I", "I", "I", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "O", "U", "U", "U", "U", "U", "U", "U", "U", "U", "U", "U", "Y", "Y", "Y", "Y", "Y", "D"};

		for (int i = 0; i < accentChars.length; i++) {
			input = input.replace(accentChars[i], noAccentChars[i]);
		}

		return input;
	}


}
