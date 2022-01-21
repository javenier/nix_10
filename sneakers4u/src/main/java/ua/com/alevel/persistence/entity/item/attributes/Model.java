package ua.com.alevel.persistence.entity.item.attributes;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.item.Sneaker;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "models")
public class Model extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "model", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Sneaker> sneakers;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Brand brand;

    public Model() {
        super();
        this.sneakers = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Sneaker> getSneakers() {
        return sneakers;
    }

    public void setSneakers(Set<Sneaker> sneakers) {
        this.sneakers = sneakers;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
