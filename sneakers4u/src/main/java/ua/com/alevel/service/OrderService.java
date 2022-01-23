package ua.com.alevel.service;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.item.Sneaker;
import ua.com.alevel.persistence.entity.order.Order;

public interface OrderService extends BaseService<Order> {

    DataTableResponse<Order> findAllByClientId(DataTableRequest request, Long clientId);

    void removeItemFromCart(Sneaker sneaker, Order order);
}
