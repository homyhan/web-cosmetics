package web.webbanhang.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import web.webbanhang.user.User;

import java.util.Optional;

public interface UserJpa extends JpaRepository<User, Integer>{

    User findByEmail(String email);
}
