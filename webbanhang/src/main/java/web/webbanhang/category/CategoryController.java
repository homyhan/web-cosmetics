package web.webbanhang.category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.webbanhang.jpa.CategoryJpa;

import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {

    private CategoryJpa categoryRepository;

    public CategoryController(CategoryJpa categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/category")
    public ResponseEntity<String> createCategory(@RequestBody Category category){

        categoryRepository.save(category);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category")
    public List<Category> getListCategory(){
        return categoryRepository.findAll();
    }

    @GetMapping("/category/{id}")
    public Category getCategoryById(@PathVariable int id){
        Optional<Category> c = categoryRepository.findById(id);

        return c.get();
    }



}
