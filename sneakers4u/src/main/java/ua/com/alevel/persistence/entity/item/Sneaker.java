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

    @ManyToMany(mappedBy = "sneakers", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Order> orders;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "sneaker_size", joinColumns = @JoinColumn(name = "sneaker_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id"))
    private Set<Size> sizes;

    @Enumerated(EnumType.STRING)
    @Column(name = "sneaker_gender")
    private Gender sneakerGender;

    @Column(name = "version_of_model")
    private String versionOfModel;

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

    public void addSize(Size size) {
        sizes.add(size);
        size.getSneakers().add(this);
    }

    public void removeSize(Size size) {
        sizes.removeIf(s -> (s.getId() == size.getId()));
        size.getSneakers().removeIf(s -> (s.getId() == this.getId()));
    }
}