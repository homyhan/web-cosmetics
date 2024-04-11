package web.webbanhang.category;

import org.springframework.http.HttpStatus;
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

    @GetMapping("/category/search/{keyword}")
    public ResponseEntity<List<Category>> searchCategoriesByName(@PathVariable String keyword) {
        try {
            // Xử lý chuỗi keyword
            keyword = keyword.toLowerCase().replaceAll("\\s+", ""); // Chuyển thành chữ thường và loại bỏ khoảng trắng

            // Tìm category bằng tên tương ứng
            List<Category> categories = categoryRepository.findByNameCategoryContainingIgnoreCase(keyword);

            if (categories.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            System.err.println("Error searching categories by name: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
