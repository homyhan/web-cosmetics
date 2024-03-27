package web.webbanhang.user;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	 private Role role;
	
	 public User(int id, String fullName, String email, String address, String phone, String password, Role role) {
	        this.id = id;
	        this.fullName = fullName;
	        this.email= email;
	        this.address = address;
	        this.phone = phone;
	        this.password = password;
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

	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + fullName + ", email=" + email + ", address=" + address + ", phone="
				+ phone + ", password=" + password + "]";
	}
	
	
	
	
}

