package web.webbanhang.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import web.webbanhang.comment.Comment;
import web.webbanhang.role.Role;

public interface CommentJpa extends JpaRepository<Comment, Integer>{

}
