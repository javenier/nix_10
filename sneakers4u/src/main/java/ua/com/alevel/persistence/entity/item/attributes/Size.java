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

    @ManyToMany(mappedBy = "sizes", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Size size1 = (Size) o;

        if (size != null ? !size.equals(size1.size) : size1.size != null) return false;
        return sneakers != null ? sneakers.equals(size1.sneakers) : size1.sneakers == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
//        result = 31 * result + (size != null ? size.hashCode() : 0);
//        result = 31 * result + (sneakers != null ? sneakers.hashCode() : 0);
        return result;
    }
}