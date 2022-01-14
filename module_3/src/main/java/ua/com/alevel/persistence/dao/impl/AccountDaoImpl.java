package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.AccountDao;
import ua.com.alevel.persistence.entity.Account;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountDaoImpl implements AccountDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Account entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(Account entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(Long id) {
        entityManager.createQuery("delete from Account a where a.id = :id").
                setParameter("id", id).
                executeUpdate();
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager.createQuery("select count(a.id) from Account a where a.id = :id").
                setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Account findById(Long id) {
        return entityManager.find(Account.class, id);
    }

    @Override
    public List<Account> findAll() {
        return entityManager.createQuery("select a from Account a", Account.class).getResultList();
    }

    @Override
    public List<Account> findAllByUserId(Long userId) {
        return entityManager.
                createQuery("select a from Account a where a.user.id = :userId", Account.class).
                setParameter("userId", userId).
                getResultList();
    }
}
