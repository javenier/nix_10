package ua.com.alevel.persistence.entity.item.attributes;

import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.item.Sneaker;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {

    private String name;

    private String imageUrl;

    @OneToMany(mappedBy = "brand", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
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
}
