package ua.com.alevel.persistence.repository.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.order.Order;
import ua.com.alevel.persistence.entity.user.Client;
import ua.com.alevel.persistence.repository.custom.ClientCustomRepository;
import ua.com.alevel.persistence.type.Gender;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class ClientCustomRepositoryImpl implements ClientCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteById(Long id) {
        entityManager.
                createQuery("delete from Client c where c.id = :id").
                setParameter("id", id).
                executeUpdate();
    }

    @Override
    public DataTableResponse<Client> findAll(DataTableRequest request) {
        List<Client> clients = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Object[]> clientsRl = entityManager.createNativeQuery("select * from users where DTYPE = 'CLIENT' order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize()).getResultList();

        for (Object[] object : clientsRl) {
            Client client = convertResultSetToClient(object);
            clients.add(client);
        }

        DataTableResponse<Client> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(clients);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    private Client convertResultSetToClient(Object[] resultSet) {
        BigInteger id = (BigInteger) resultSet[1];
        Timestamp created = (Timestamp) resultSet[2];
        Timestamp updated = (Timestamp) resultSet[3];
        String email = (String) resultSet[4];
        Boolean enabled = (Boolean) resultSet[5];
        Integer age = (Integer) resultSet[8];
        String firstName = (String) resultSet[9];
        String gender = (String) resultSet[10];
        String lastName = (String) resultSet[11];
        Client client = new Client();
        client.setId(id.longValue());
        client.setCreated(new Date(created.getTime()));
        client.setUpdated(new Date(updated.getTime()));
        client.setEmail(email);
        client.setEnabled(enabled);
        client.setAge(age);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        if(gender != null)
            client.setGender(gender.equals("MALE") ? Gender.MALE : Gender.FEMALE);
        return client;
    }


    @Override
    public void disableClient(Long id) {
        entityManager.
                createQuery("update User u set u.enabled = false where u.id = :id").
                setParameter("id", id).
                executeUpdate();
    }

    @Override
    public void enableClient(Long id) {
        entityManager.
                createQuery("update User u set u.enabled = true where u.id = :id").
                setParameter("id", id).
                executeUpdate();
    }

    @Override
    public Long findUnfinishedOrderId(Long clientId) {
        Long orderId;
        try {
            orderId = (Long) entityManager.
                    createQuery("select o.id from Client c inner join Order o " +
                            "on c.id = o.client.id where o.finished = false and o.client.id = :clientId").
                    setParameter("clientId", clientId).
                    getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return orderId;
    }
}
