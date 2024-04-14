package web.webbanhang.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.webbanhang.category.Category;
import web.webbanhang.role.Role;

public interface RoleJpa extends JpaRepository<Role, Integer>{
    @Query("SELECT r FROM Role r WHERE r.role = :roleCode")
    Role findByRoleCode(@Param("roleCode") int roleCode);
}
