package ua.com.alevel.persistence.entity.item.attributes;

import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.item.Sneaker;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sizes")
public class Size extends BaseEntity {

    private Integer size;

    @ManyToMany(mappedBy = "sizes", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Sneaker> sneakers;

    public Size() {
        super();
        this.sneakers = new HashSet<>();
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Set<Sneaker> getSneakers() {
        return sneakers;
    }

    public void setSneakers(Set<Sneaker> sneakers) {
        this.sneakers = sneakers;
    }
}