package web.webbanhang.category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.webbanhang.jpa.CategoryJpa;
import web.webbanhang.product.Product;

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


    @PutMapping("/category/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable int id, @RequestBody Category updatedCategory) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (!optionalCategory.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Category existingCategory = optionalCategory.get();

        existingCategory.setNameCategory(updatedCategory.getNameCategory());

        categoryRepository.save(existingCategory);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (!optionalCategory.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Category categoryToDelete = optionalCategory.get();
        categoryRepository.delete(categoryToDelete);

        return ResponseEntity.noContent().build();
    }

}
