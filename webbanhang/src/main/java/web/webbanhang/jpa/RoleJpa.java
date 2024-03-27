package web.webbanhang.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import web.webbanhang.role.Role;

public interface RoleJpa extends JpaRepository<Role, Integer>{

}
