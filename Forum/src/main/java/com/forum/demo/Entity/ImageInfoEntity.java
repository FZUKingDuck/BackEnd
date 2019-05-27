package com.forum.demo.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "image_info", schema = "forum", catalog = "")
public class ImageInfoEntity {
    private String id;
    private String url;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageInfoEntity that = (ImageInfoEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url);
    }
}
