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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Client> clients = entityManager.createNativeQuery("select id, created, updated, first_name, last_name, age, count(*) as bankCount" +
        " from clients join bank_client bc on clients.id = bc.client_id group by client_id order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize(), Client.class).getResultList();

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
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Client> clients = entityManager.createNativeQuery("select id, created, updated, first_name, last_name, age, count(*) as bankCount" +
                " from clients join bank_client bc on clients.id = bc.client_id where bank_id = " +
                bankId + " group by client_id order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize(), Client.class).getResultList();

        DataTableResponse<Client> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(clients);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }
}