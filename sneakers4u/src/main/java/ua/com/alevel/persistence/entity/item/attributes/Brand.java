package ua.com.alevel.persistence.entity.item.attributes;

import ua.com.alevel.persistence.entity.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {

    private String name;

    private String imageUrl;

    @OneToMany(mappedBy = "brand", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Model> models;

    public Brand() {
        super();
        this.models = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Model> getModels() {
        return models;
    }

    public void setModels(Set<Model> models) {
        this.models = models;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Brand brand = (Brand) o;

        if (name != null ? !name.equals(brand.name) : brand.name != null) return false;
        if (imageUrl != null ? !imageUrl.equals(brand.imageUrl) : brand.imageUrl != null) return false;
        return models != null ? models.equals(brand.models) : brand.models == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
//        result = 31 * result + (name != null ? name.hashCode() : 0);
//        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
//        result = 31 * result + (models != null ? models.hashCode() : 0);
        return result;
    }
}
