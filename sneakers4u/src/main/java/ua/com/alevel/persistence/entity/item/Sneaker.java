package ua.com.alevel.persistence.entity.item;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ua.com.alevel.persistence.entity.item.attributes.Brand;
import ua.com.alevel.persistence.entity.item.attributes.Model;
import ua.com.alevel.persistence.entity.item.attributes.Size;
import ua.com.alevel.persistence.entity.order.Order;
import ua.com.alevel.persistence.type.Gender;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sneakers")
public class Sneaker extends Item {

    @ManyToOne
    @JoinColumn(name = "model_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Model model;

    @ManyToMany(mappedBy = "sneakers", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Order> orders;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "sneaker_size", joinColumns = @JoinColumn(name = "sneaker_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id"))
    private Set<Size> sizes;

    @Enumerated(EnumType.STRING)
    @Column(name = "sneaker_gender")
    private Gender sneakerGender;

    @Column(name = "version_of_model")
    private String versionOfModel;

    @Column(name = "full_name")
    private String fullName;

    public Sneaker() {
        super();
        this.orders = new HashSet<>();
        this.sizes = new HashSet<>();
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Gender getSneakerGender() {
        return sneakerGender;
    }

    public void setSneakerGender(Gender sneakerGender) {
        this.sneakerGender = sneakerGender;
    }

    public String getVersionOfModel() {
        return versionOfModel;
    }

    public void setVersionOfModel(String versionOfModel) {
        this.versionOfModel = versionOfModel;
    }

    public Set<Size> getSizes() {
        return sizes;
    }

    public void setSizes(Set<Size> sizes) {
        this.sizes = sizes;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void addSize(Size size) {
        sizes.add(size);
        size.getSneakers().add(this);
    }

    public void removeSize(Size size) {
        sizes.removeIf(s -> (s.getId() == size.getId()));
        size.getSneakers().removeIf(s -> (s.getId() == this.getId()));
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//
//        Sneaker sneaker = (Sneaker) o;
//
//        if (model != null ? !model.equals(sneaker.model) : sneaker.model != null) return false;
//        if (orders != null ? !orders.equals(sneaker.orders) : sneaker.orders != null) return false;
//        if (sizes != null ? !sizes.equals(sneaker.sizes) : sneaker.sizes != null) return false;
//        if (sneakerGender != sneaker.sneakerGender) return false;
//        return versionOfModel != null ? versionOfModel.equals(sneaker.versionOfModel) : sneaker.versionOfModel == null;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = super.hashCode();
////        result = 31 * result + (model != null ? model.hashCode() : 0);
////        result = 31 * result + (orders != null ? orders.hashCode() : 0);
////        result = 31 * result + (sizes != null ? sizes.hashCode() : 0);
////        result = 31 * result + (sneakerGender != null ? sneakerGender.hashCode() : 0);
////        result = 31 * result + (versionOfModel != null ? versionOfModel.hashCode() : 0);
//        return result;
//    }
}