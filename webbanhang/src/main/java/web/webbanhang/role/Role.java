package web.webbanhang.role;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import web.webbanhang.user.User;

@Entity
public class Role {
	protected Role() {
		
	}
	@Id
	@GeneratedValue
	private int id;
	private int role;
	private String nameRole;
	
//	 @OneToMany(mappedBy = "role")
	@OneToMany(mappedBy="role")
	@JsonIgnore
	    private List<User> users;
	
	public Role(int id, int role, String nameRole) {
		super();
		this.id = id;
		this.role = role;
		this.nameRole = nameRole;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public String getNameRole() {
		return nameRole;
	}
	public void setNameRole(String nameRole) {
		this.nameRole = nameRole;
	}

	public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + role + ", nameRole=" + nameRole + "]";
	}
	
}
