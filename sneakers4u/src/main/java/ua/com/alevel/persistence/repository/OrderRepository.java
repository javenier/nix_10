package ua.com.alevel.persistence.repository;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.order.Order;

@Repository
public interface OrderRepository extends BaseRepository<Order> {
}
