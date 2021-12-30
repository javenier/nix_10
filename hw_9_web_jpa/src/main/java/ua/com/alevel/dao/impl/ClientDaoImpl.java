package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.BankDao;
import ua.com.alevel.dao.ClientDao;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Bank;
import ua.com.alevel.entity.Client;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class ClientDaoImpl implements ClientDao {

    private final BankDao bankDao;

    @PersistenceContext
    private EntityManager entityManager;

    public ClientDaoImpl(BankDao bankDao) {
        this.bankDao = bankDao;
    }

    @Override
    public void update(Client entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(Long id) {
        entityManager.createQuery("delete from Client c where c.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager.createQuery("select count(c.id) from Client c where c.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Client findById(Long id) {
        return entityManager.find(Client.class, id);
    }

    @Override
    public DataTableResponse<Client> findAll(DataTableRequest request) {
        List<Client> clients = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Object[]> clientsRl = entityManager.createNativeQuery("select id, created, updated, first_name, last_name, age, count(*) as bankCount" +
        " from clients join bank_client bc on clients.id = bc.client_id group by client_id order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize()).getResultList();

        for(Object[] object : clientsRl) {
            ClientDaoImpl.ClientResultSet clientRs = convertResultSetToClient(object);
            clients.add(clientRs.getClient());
            otherParamMap.put(clientRs.getClient().getId(), clientRs.getBankCount());
        }

        DataTableResponse<Client> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(clients);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    @Override
    public long count() {
        Query query = entityManager.createQuery("select count(c) from Client c");
        return (long) query.getSingleResult();
    }

    @Override
    public void create(Client client, Long bankId) {
        Bank bank = bankDao.findById(bankId);
        bank.addClient(client);
        entityManager.persist(client);
    }

    @Override
    public DataTableResponse<Client> findAllByBankId(DataTableRequest request, Long bankId) {
        List<Client> clients = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Object[]> clientsRl = entityManager.createNativeQuery("select id, created, updated, first_name, last_name, age, count(*) as bankCount" +
                " from clients join bank_client bc on clients.id = bc.client_id where bank_id = " +
                bankId + " group by client_id order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize()).getResultList();

        for(Object[] object : clientsRl) {
            ClientDaoImpl.ClientResultSet clientRs = convertResultSetToClient(object);
            clients.add(clientRs.getClient());
            otherParamMap.put(clientRs.getClient().getId(), clientRs.getBankCount());
        }

        DataTableResponse<Client> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(clients);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    private ClientResultSet convertResultSetToClient(Object[] resultSet) {
        BigInteger id = (BigInteger) resultSet[0];
        Timestamp created = (Timestamp) resultSet[1];
        Timestamp updated = (Timestamp) resultSet[2];
        String firstName = (String) resultSet[3];
        String lastName = (String) resultSet[4];
        Integer age = (Integer) resultSet[5];
        BigInteger bankCount = (BigInteger) resultSet[6];
        Client client = new Client();
        client.setId(id.longValue());
        client.setCreated(new Date(created.getTime()));
        client.setUpdated(new Date(updated.getTime()));
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setAge(age);
        return new ClientResultSet(client, bankCount.intValue());
    }

    private static class ClientResultSet {

        private final Client client;
        private final int bankCount;

        private ClientResultSet(Client client, int bankCount) {
            this.client = client;
            this.bankCount = bankCount;
        }

        public Client getClient() {
            return client;
        }

        public int getBankCount() {
            return bankCount;
        }
    }
}
