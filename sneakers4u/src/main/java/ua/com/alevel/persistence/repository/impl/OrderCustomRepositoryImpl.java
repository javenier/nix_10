package ua.com.alevel.persistence.repository.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.order.Order;
import ua.com.alevel.persistence.repository.ClientRepository;
import ua.com.alevel.persistence.repository.custom.OrderCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final ClientRepository clientRepository;

    public OrderCustomRepositoryImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void deleteById(Long id) {
        entityManager.
                createQuery("delete from Order r where r.id = :id").
                setParameter("id", id).
                executeUpdate();
    }

    @Override
    public DataTableResponse<Order> findAll(DataTableRequest request) {
        List<Order> orders = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Object[]> ordersRl = entityManager.createNativeQuery("select * from orders order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize()).getResultList();

        for (Object[] object : ordersRl) {
            Order order = convertResultSetToOrder(object);
            orders.add(order);
        }

        DataTableResponse<Order> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(orders);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    private Order convertResultSetToOrder(Object[] resultSet) {
        BigInteger id = (BigInteger) resultSet[0];
        Timestamp created = (Timestamp) resultSet[1];
        Timestamp updated = (Timestamp) resultSet[2];
        String address = (String) resultSet[3];
        String comment = (String) resultSet[4];
        boolean finished = (boolean) resultSet[5];
        Integer postOffice = (Integer) resultSet[6];
        BigInteger totalPrice = (BigInteger) resultSet[7];
        BigInteger clientId = (BigInteger) resultSet[8];
        Order order = new Order();
        order.setId(id.longValue());
        order.setCreated(new Date(created.getTime()));
        order.setUpdated(new Date(updated.getTime()));
        order.setAddress(address);
        order.setComment(comment);
        order.setFinished(finished);
        order.setPostOffice(postOffice);
        order.setTotalPrice(totalPrice.longValue());
        order.setClient(clientRepository.getById(clientId.longValue()));
        return order;
    }

    @Override
    public DataTableResponse<Order> findAllByClientId(DataTableRequest request, Long clientId) {
        List<Order> orders = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Object[]> ordersRl = entityManager.createNativeQuery("select * from orders where client_id =" +
                " " + clientId + " order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize()).getResultList();

        for (Object[] object : ordersRl) {
            Order order = convertResultSetToOrder(object);
            orders.add(order);
        }

        DataTableResponse<Order> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(orders);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }
}
