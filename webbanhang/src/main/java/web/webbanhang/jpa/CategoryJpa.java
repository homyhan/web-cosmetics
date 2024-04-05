package web.webbanhang.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.webbanhang.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpa extends JpaRepository<Category, Integer>{
    @Query("SELECT c FROM Category c WHERE c.nameCategory = :nameCategory")
    Category findByNameCategory(@Param("nameCategory") String nameCategory);
}
