package web.webbanhang.banners;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.webbanhang.jpa.BannerJpa;

import java.util.List;
import java.util.Optional;

@RestController
public class BannersController {
    private BannerJpa bannerJpa;

    public BannersController(BannerJpa bannerJpa) {
        this.bannerJpa = bannerJpa;
    }

    @PostMapping("/banner")
    public ResponseEntity<String> createBanner(@RequestBody Banners banners) {
        Banners addedBanner = bannerJpa.save(banners);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/banners")
    public ResponseEntity<List<Banners>> getAllBanners() {
        try {
            List<Banners> banners = bannerJpa.findAll();
            return ResponseEntity.ok(banners);
        } catch (Exception e) {
            System.err.println("Error retrieving users: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/banner/{id}")
    public Banners getBannerById(@PathVariable int id){
        Banners banner = bannerJpa.findById(id).get();
        return banner;
    }

    @PutMapping("/banners/{id}")
    public ResponseEntity<String> updateBanner(@PathVariable int id, @RequestBody Banners updatedBanner) {
        Optional<Banners> optionalBanner = bannerJpa.findById(id);

        if (!optionalBanner.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Banners existingBanner = optionalBanner.get();

        existingBanner.setName_banner(updatedBanner.getName_banner());
        existingBanner.setImg(updatedBanner.getImg());
        existingBanner.setContent(updatedBanner.getContent());

        bannerJpa.save(existingBanner);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/banners/{id}")
    public ResponseEntity<String> deleteBanner(@PathVariable int id) {
        Optional<Banners> optionalBanner = bannerJpa.findById(id);

        if (!optionalBanner.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Banners bannerToDelete = optionalBanner.get();
        bannerJpa.delete(bannerToDelete);

        return ResponseEntity.noContent().build();
    }
}
