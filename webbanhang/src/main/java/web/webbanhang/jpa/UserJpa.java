package web.webbanhang.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import web.webbanhang.user.User;

public interface UserJpa extends JpaRepository<User, Integer>{

}
