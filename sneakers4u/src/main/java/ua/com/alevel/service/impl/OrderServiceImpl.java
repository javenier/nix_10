package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.entity.item.Sneaker;
import ua.com.alevel.persistence.entity.item.attributes.Brand;
import ua.com.alevel.persistence.entity.order.Order;
import ua.com.alevel.persistence.repository.OrderRepository;
import ua.com.alevel.persistence.repository.custom.OrderCustomRepository;
import ua.com.alevel.service.OrderService;

import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderCustomRepository orderCustomRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderCustomRepository orderCustomRepository) {
        this.orderRepository = orderRepository;
        this.orderCustomRepository = orderCustomRepository;
    }

    @Override
    public void create(Order entity) {
        orderRepository.save(entity);
    }

    @Override
    public void update(Order entity) {
        if(!orderRepository.existsById(entity.getId())) {
            throw new EntityNotFoundException("not found...");
        }
        orderRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if(!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("not found...");
        }
        orderCustomRepository.deleteById(id);
    }

    @Override
    public Order findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent())
            return order.get();
        else
            throw new EntityNotFoundException("not found...");
    }

    @Override
    public DataTableResponse<Order> findAll(DataTableRequest request) {
        DataTableResponse<Order> dataTableResponse = orderCustomRepository.findAll(request);
        long count = orderRepository.count();
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    @Override
    public DataTableResponse<Order> findAllByClientId(DataTableRequest request, Long clientId) {
        DataTableResponse<Order> dataTableResponse = orderCustomRepository.findAllByClientId(request, clientId);
        long count = orderRepository.countByClientId(clientId);
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    @Override
    public void removeItemFromCart(Sneaker sneaker, Order order) {
        order.getSneakers().removeIf(s -> (s.getId() == sneaker.getId()));
        sneaker.getOrders().removeIf(o -> (o.getId() == order.getId()));
        order.getSneakerSizeForCurrentOrder().remove(sneaker.getId());
        Long currentPrice = order.getTotalPrice();
        currentPrice -= sneaker.getPrice();
        order.setTotalPrice(currentPrice);
        orderRepository.save(order);
    }
}
