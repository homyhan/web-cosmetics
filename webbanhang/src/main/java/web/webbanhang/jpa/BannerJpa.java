package web.webbanhang.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import web.webbanhang.banners.Banners;

public interface BannerJpa extends JpaRepository<Banners, Integer> {
}
