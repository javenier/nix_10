package ua.com.alevel.persistence.entity.order;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.item.Sneaker;
import ua.com.alevel.persistence.entity.user.Client;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(name = "total_price")
    private Long totalPrice;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client client;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "order_sneaker", joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "sneaker_id"))
    private Set<Sneaker> sneakers;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private String address;

    @Column(name = "post_office")
    private Integer postOffice;

    @ElementCollection
    @CollectionTable(name = "sneaker_size_current_order", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "sneaker_id")
    @Column(name = "size_id")
    private Map<Long, Long> sneakerSizeForCurrentOrder;

    boolean finished;

    public Order() {
        super();
        this.sneakers = new HashSet<>();
        this.sneakerSizeForCurrentOrder = new HashMap<>();
        totalPrice = 0L;
        finished = false;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Sneaker> getSneakers() {
        return sneakers;
    }

    public void setSneakers(Set<Sneaker> sneakers) {
        this.sneakers = sneakers;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(Integer postOffice) {
        this.postOffice = postOffice;
    }

    public Map<Long, Long> getSneakerSizeForCurrentOrder() {
        return sneakerSizeForCurrentOrder;
    }

    public void setSneakerSizeForCurrentOrder(Map<Long, Long> sneakerSizeForCurrentOrder) {
        this.sneakerSizeForCurrentOrder = sneakerSizeForCurrentOrder;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Order order = (Order) o;

        if (totalPrice != null ? !totalPrice.equals(order.totalPrice) : order.totalPrice != null) return false;
        if (client != null ? !client.equals(order.client) : order.client != null) return false;
        if (sneakers != null ? !sneakers.equals(order.sneakers) : order.sneakers != null) return false;
        if (comment != null ? !comment.equals(order.comment) : order.comment != null) return false;
        if (address != null ? !address.equals(order.address) : order.address != null) return false;
        return postOffice != null ? postOffice.equals(order.postOffice) : order.postOffice == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (sneakers != null ? sneakers.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (postOffice != null ? postOffice.hashCode() : 0);
        return result;
    }
}