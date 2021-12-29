package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dao.BankDao;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Bank;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BankDaoImpl implements BankDao {

    @PersistenceContext
    private EntityManager entityManager;

    private final ClientDaoImpl clientDao;

    public BankDaoImpl(ClientDaoImpl clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public void create(Bank bank) {
        entityManager.persist(bank);
    }

    @Override
    public DataTableResponse<Bank> findAllByClientId(DataTableRequest request, Long clientId) {
        return null;
    }

    @Override
    public void link(Long bankId, Long clientId) {
        Bank bank = findById(bankId);
        bank.addClient(clientDao.findById(clientId));
    }

    @Override
    public void unlink(Long bankId, Long clientId) {

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

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bank> criteriaQuery = criteriaBuilder.createQuery(Bank.class);
        Root<Bank> from = criteriaQuery.from(Bank.class);
        if (request.getOrder().equals("desc")) {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get(request.getSort())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(request.getSort())));
        }

        List<Bank> banks = entityManager.createQuery(criteriaQuery)
                .setFirstResult(limit)
                .setMaxResults(request.getPageSize())
                .getResultList();

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
