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

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Model model = (Model) o;

        if (name != null ? !name.equals(model.name) : model.name != null) return false;
        if (sneakers != null ? !sneakers.equals(model.sneakers) : model.sneakers != null) return false;
        return brand != null ? brand.equals(model.brand) : model.brand == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
//        result = 31 * result + (name != null ? name.hashCode() : 0);
//        result = 31 * result + (sneakers != null ? sneakers.hashCode() : 0);
//        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        return result;
    }
}
