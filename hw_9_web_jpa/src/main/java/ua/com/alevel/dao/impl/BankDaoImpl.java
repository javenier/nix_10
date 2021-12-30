package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.BankDao;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Bank;
import ua.com.alevel.entity.Client;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BankDaoImpl implements BankDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Bank bank) {
        entityManager.persist(bank);
    }

    @Override
    public DataTableResponse<Bank> findAllByClientId(DataTableRequest request, Long clientId) {
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Bank> banks = entityManager.createNativeQuery("select id, created, updated, name, year_of_foundation, bank_type, count(bank_id) as clientCount " +
                "from banks as bank left join bank_client as bc on bank.id = bc.bank_id where client_id = " + clientId +
                " group by bank.id order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize(), Bank.class).getResultList();

        DataTableResponse<Bank> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(banks);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    @Override
    public void link(Long bankId, Client client) {
        Bank bank = findById(bankId);
        bank.addClient(client);
        entityManager.merge(bank);
    }

    //not work because findByClient
    @Override
    public void unlink(Long bankId, Client client) {
        Bank bank = findById(bankId);
        bank.removeClient(client);
        entityManager.merge(bank);
    }

    @Override
    public void update(Bank entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(Long id) {
        entityManager.createQuery("delete from Bank b where b.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager.createQuery("select count(b.id) from Bank b where b.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Bank findById(Long id) {
        return entityManager.find(Bank.class, id);
    }

    @Override
    public DataTableResponse<Bank> findAll(DataTableRequest request) {
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Bank> banks = entityManager.createNativeQuery("select id, created, updated, name, year_of_foundation, bank_type, count(bank_id) as clientCount " +
                "from banks as bank left join bank_client as bc on bank.id = bc.bank_id" +
                " group by bank.id order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize(), Bank.class).getResultList();

        DataTableResponse<Bank> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(banks);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    @Override
    public long count() {
        Query query = entityManager.createQuery("select count(b) from Bank b");
        return (long) query.getSingleResult();
    }
}
