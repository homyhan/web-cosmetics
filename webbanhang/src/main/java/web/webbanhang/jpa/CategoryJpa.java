package web.webbanhang.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.webbanhang.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryJpa extends JpaRepository<Category, Integer>{
    @Query("SELECT c FROM Category c WHERE c.nameCategory = :nameCategory")
    Category findByNameCategory(@Param("nameCategory") String nameCategory);

    List<Category> findByNameCategoryContainingIgnoreCase(String keyword);
}
