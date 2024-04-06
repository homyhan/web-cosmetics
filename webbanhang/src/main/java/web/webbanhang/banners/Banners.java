package web.webbanhang.banners;

import jakarta.persistence.*;

@Entity
public class Banners {

    @Id
    @GeneratedValue
    private int id;

    private String name_banner;

    private String img;

    private String content;

    public Banners(int id, String name_banner, String img, String content) {
        this.id = id;
        this.name_banner = name_banner;
        this.img = img;
        this.content = content;
    }

    public Banners() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_banner() {
        return name_banner;
    }

    public void setName_banner(String name_banner) {
        this.name_banner = name_banner;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Banners{" +
                "id=" + id +
                ", name_banner='" + name_banner + '\'' +
                ", img='" + img + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
