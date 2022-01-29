package ua.com.alevel.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.exception.DuplicateItemInOrderException;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.entity.item.Sneaker;
import ua.com.alevel.persistence.entity.item.attributes.Size;
import ua.com.alevel.persistence.entity.order.Order;
import ua.com.alevel.persistence.entity.user.Client;
import ua.com.alevel.persistence.repository.ClientRepository;
import ua.com.alevel.persistence.repository.OrderRepository;
import ua.com.alevel.persistence.repository.custom.ClientCustomRepository;
import ua.com.alevel.persistence.repository.custom.SneakerCustomRepository;
import ua.com.alevel.persistence.repository.SneakerRepository;
import ua.com.alevel.persistence.type.Gender;
import ua.com.alevel.service.SneakerService;

import java.util.*;

@Service
public class SneakerServiceImpl implements SneakerService {

    private final SneakerRepository sneakerRepository;
    private final SneakerCustomRepository sneakerCustomRepository;
    private final OrderRepository orderRepository;
    private final ClientCustomRepository clientCustomRepository;
    private final ClientRepository clientRepository;

    public SneakerServiceImpl(SneakerRepository sneakerRepository, SneakerCustomRepository sneakerCustomRepository, OrderRepository orderRepository, ClientCustomRepository clientCustomRepository, ClientRepository clientRepository) {
        this.sneakerRepository = sneakerRepository;
        this.sneakerCustomRepository = sneakerCustomRepository;
        this.orderRepository = orderRepository;
        this.clientCustomRepository = clientCustomRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public void create(Sneaker entity) {
        sneakerRepository.save(entity);
    }

    @Override
    public void update(Sneaker entity) {
        if(!sneakerRepository.existsById(entity.getId())) {
            throw new EntityNotFoundException("not found...");
        }
        sneakerRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if(!sneakerRepository.existsById(id)) {
            throw new EntityNotFoundException("not found...");
        }
        sneakerCustomRepository.deleteById(id);
    }

    @Override
    public Sneaker findById(Long id) {
        Optional<Sneaker> sneaker = sneakerRepository.findById(id);
        if(sneaker.isPresent())
            return sneaker.get();
        else
            throw new EntityNotFoundException("not found...");
    }

    @Override
    public DataTableResponse<Sneaker> findAll(DataTableRequest request) {
        DataTableResponse<Sneaker> dataTableResponse = sneakerCustomRepository.findAll(request);
        long count = sneakerRepository.count();
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    //change count
    @Override
    public DataTableResponse<Sneaker> findAllByBrandId(DataTableRequest request, Long brandId) {
        DataTableResponse<Sneaker> dataTableResponse = sneakerCustomRepository.findAllByBrandId(request, brandId);
        long count = sneakerCustomRepository.countByBrandId(brandId);
        System.out.println(count);
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    @Override
    public DataTableResponse<Sneaker> findAllByModelId(DataTableRequest request, Long modelId) {
        DataTableResponse<Sneaker> dataTableResponse = sneakerCustomRepository.findAllByModelId(request, modelId);
        long count = sneakerRepository.countByModelId(modelId);
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    @Override
    public DataTableResponse<Sneaker> findAllByOrderId(DataTableRequest request, Long orderId) {
        DataTableResponse<Sneaker> dataTableResponse = sneakerCustomRepository.findAllByOrderId(request, orderId);
        long count = sneakerRepository.count();
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    @Override
    public DataTableResponse<Sneaker> findAllByGender(DataTableRequest request, String gender) {
        DataTableResponse<Sneaker> dataTableResponse = sneakerCustomRepository.findAllByGender(request, gender);
        long count = sneakerRepository.countBySneakerGender(gender.equals("'MALE'") ? Gender.MALE : Gender.FEMALE);
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    @Override
    public DataTableResponse<Sneaker> findAllBySearchQuery(DataTableRequest request, String query) {
        DataTableResponse<Sneaker> dataTableResponse = sneakerCustomRepository.findAllBySearchQuery(request, query);
        long count = sneakerRepository.countByFullNameContaining(query);
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    @Override
    public void addToCart(Sneaker sneaker, Size size) {
        User loggedInUser = (User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        Client client = clientRepository.findByEmail(loggedInUser.getUsername());
        Long unfinishedOrderId = clientCustomRepository.findUnfinishedOrderId(client.getId());
        if(unfinishedOrderId != null) {
            Optional<Order> unfinishedOrder = orderRepository.findById(unfinishedOrderId);
            if(unfinishedOrder.isPresent()) {
                Order order = unfinishedOrder.get();
                Long currentPrice = order.getTotalPrice();
                currentPrice += sneaker.getPrice();
                order.setTotalPrice(currentPrice);
                if(!order.getSneakers().add(sneaker))
                    throw new DuplicateItemInOrderException("This item has been already added to the cart.");
                order.getSneakerSizeForCurrentOrder().put(sneaker.getId(), size.getId());
                orderRepository.save(order);
            } else {
                throw new EntityNotFoundException("order not found...");
            }
        } else {
            Order order = new Order();
            order.setTotalPrice(sneaker.getPrice());
            order.setClient(client);
            Set<Sneaker> sneakers = new HashSet<>();
            sneakers.add(sneaker);
            order.setSneakers(sneakers);
            Map<Long, Long> sneakerSize = new HashMap<>();
            sneakerSize.put(sneaker.getId(), size.getId());
            order.setSneakerSizeForCurrentOrder(sneakerSize);
            orderRepository.save(order);
        }
    }
}
