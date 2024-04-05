package web.webbanhang.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.webbanhang.category.Category;
import web.webbanhang.jpa.CategoryJpa;
import web.webbanhang.jpa.ProductJpa;

import java.util.List;

@RestController
public class ProductController {
    private ProductJpa productRepositoty;

    private CategoryJpa categoryRepository;

    public ProductController(ProductJpa productRepositoty, CategoryJpa categoryRepository) {
        this.productRepositoty = productRepositoty;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/products")
    public ResponseEntity<String> createProduct(@RequestBody Product product){
       Category cate = categoryRepository.findByNameCategory(product.getCategory().getNameCategory());

        if (cate == null) {

            System.out.println("Loi 2");
        }
        product.setCategory(cate);
        Product addedProd = productRepositoty.save(product);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/products")
    public ResponseEntity<List<Product>> retrieveAllProduct() {
        try {
            List<Product> products = productRepositoty.findAll();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            System.err.println("Error retrieving users: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable int id){
        Product prod = productRepositoty.findById(id).get();
        return prod;
    }
}
