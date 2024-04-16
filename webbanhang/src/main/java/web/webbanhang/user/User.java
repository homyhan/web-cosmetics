package web.webbanhang.user;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import web.webbanhang.cart.Cart;

import web.webbanhang.comment.Comment;
import web.webbanhang.product.Product;

import web.webbanhang.role.Role;
@Entity
public class User {
	protected User() {
		
	}
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name="fullname")
	private String fullName;

	private String email;

	private String address;

	private String phone;

	private String password;
	private int status;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	 private Role role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Cart> carts = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Comment> comments = new ArrayList<>();
	
	 public User(int id, String fullName, String email, String address, String phone, String password,  int status,  Role role) {
	        this.id = id;
	        this.fullName = fullName;
	        this.email= email;
	        this.address = address;
	        this.phone = phone;
	        this.password = password;
			this.status = status;
	        this.role =role;
	    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	public List<Cart> getCarts() {
		return carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	//	public void addToCart(Product product, int quantity) {
//		Cart cart = new Cart(this, product, quantity);
//		carts.add(cart);
//		product.setQuantity(product.getQuantity() - quantity); // Giảm số lượng sản phẩm khi thêm vào giỏ hàng
//	}
	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + fullName + ", email=" + email + ", address=" + address + ", phone="
				+ phone + ", password=" + password + ", status=" + status + "]";
	}
	
	
	
	
}

