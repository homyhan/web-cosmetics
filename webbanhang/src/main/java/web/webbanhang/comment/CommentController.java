package web.webbanhang.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.webbanhang.jpa.CommentJpa;
import web.webbanhang.jpa.ProductJpa;
import web.webbanhang.jpa.UserJpa;
import web.webbanhang.product.Product;
import web.webbanhang.user.User;

import java.util.Optional;

@RestController
public class CommentController {
    private CommentJpa commentRepository;
    private UserJpa userRepository;
    private ProductJpa productRepository;

    public CommentController(CommentJpa commentRepository, UserJpa userRepository, ProductJpa productRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

//    @PostMapping("/comment")
//    public ResponseEntity<String> addComment(@RequestBody Comment comment) {
//        try {
//            // Lấy thông tin User từ comment
//            User user = userRepository.findById(comment.getUser().getId())
//                    .orElseThrow(() -> new Exception("User not found with id: " + comment.getUser().getId()));
//
//            // Lấy thông tin Product từ comment
//            Product product = productRepository.findById(comment.getProduct().getId())
//                    .orElseThrow(() -> new Exception("Product not found with id: " + comment.getProduct().getId()));
//
//            // Gán lại User và Product cho Comment
//            comment.setUser(user);
//            comment.setProduct(product);
//
//            // Lưu Comment vào cơ sở dữ liệu
//            Comment savedComment = commentRepository.save(comment);
//
//            if (savedComment != null) {
//                return ResponseEntity.ok("Comment added successfully with ID: " + savedComment.getId());
//            } else {
//                return ResponseEntity.badRequest().body("Failed to add comment.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
//        }
//    }


    @PostMapping("/comments")
    public ResponseEntity<String> addComment(@RequestBody CommentRequest commentRequest) {
        try {
            int userId = commentRequest.getUserId();
            int productId = commentRequest.getProductId();
            String contentComment = commentRequest.getContentComment();
            int quantityStart = commentRequest.getQuantityStart();

            Optional<User> optionalUser = userRepository.findById(userId);
            Optional<Product> optionalProduct = productRepository.findById(productId);

            if (!optionalUser.isPresent() || !optionalProduct.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            User user = optionalUser.get();
            Product product = optionalProduct.get();

            Comment newComment = new Comment(user, product, contentComment, quantityStart);
            commentRepository.save(newComment);

            return ResponseEntity.ok("Comment added successfully");
        } catch (Exception e) {
            System.err.println("Error adding comment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding comment");
        }
    }

    @GetMapping("/commentsPage")
    public ResponseEntity<Page<Comment>> retrieveAllProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Comment> comments = commentRepository.findAll(pageRequest);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            System.err.println("Error retrieving products: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable int commentId, @RequestParam int userId) {
        try {
            Optional<Comment> optionalComment = commentRepository.findById(commentId);
            if (!optionalComment.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Comment comment = optionalComment.get();
            if (comment.getUser().getId() != userId) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to delete this comment.");
            }

            commentRepository.delete(comment);

            return ResponseEntity.ok("Comment with ID " + commentId + " deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting comment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting comment");
        }
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable int commentId,
            @RequestParam int userId,
            @RequestBody CommentRequest commentRequest) {
        try {
            Optional<Comment> optionalComment = commentRepository.findById(commentId);
            if (!optionalComment.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Comment comment = optionalComment.get();
            if (comment.getUser().getId() != userId) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to update this comment.");
            }

            comment.setContentComment(commentRequest.getContentComment());
            comment.setQuantityStart(commentRequest.getQuantityStart());
            commentRepository.save(comment);

            return ResponseEntity.ok("Comment with ID " + commentId + " updated successfully.");
        } catch (Exception e) {
            System.err.println("Error updating comment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating comment");
        }
    }


}
