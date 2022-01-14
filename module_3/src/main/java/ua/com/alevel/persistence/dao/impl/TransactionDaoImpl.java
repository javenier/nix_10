package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.jpa.JpaConfig;
import ua.com.alevel.persistence.dao.AccountDao;
import ua.com.alevel.persistence.dao.CategoryDao;
import ua.com.alevel.persistence.dao.TransactionDao;
import ua.com.alevel.persistence.entity.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TransactionDaoImpl implements TransactionDao {

    private final JpaConfig jpaConfig;
    private final AccountDao accountDao;
    private final CategoryDao categoryDao;

    @PersistenceContext
    private EntityManager entityManager;

    public TransactionDaoImpl(JpaConfig jpaConfig, AccountDao accountDao, CategoryDao categoryDao) {
        this.jpaConfig = jpaConfig;
        this.accountDao = accountDao;
        this.categoryDao = categoryDao;
    }

    @Override
    public void create(Transaction entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(Transaction entity) {

    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager.createQuery("select count(t.id) from Transaction t where t.id = :id").
                setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Transaction findById(Long id) {
        return entityManager.find(Transaction.class, id);
    }

    @Override
    public List<Transaction> findAll() {
        return entityManager.createQuery("select t from Transaction t", Transaction.class).getResultList();
    }

    @Override
    public List<Transaction> findAllByAccountId(Long accountId) {
        return entityManager.
                createQuery("select t from Transaction t where t.account.id = :accountId", Transaction.class).
                setParameter("accountId", accountId).
                getResultList();
    }

    @Override
    public List<Transaction> findAllByAccountIdJdbc(Long accountId) {
        List<Transaction> transactions = new ArrayList<>();
        try (ResultSet resultSet = jpaConfig.
                getStatement().
                executeQuery("select * from transactions where account_id = " + accountId)) {
            while(resultSet.next()) {
                transactions.add(convertResultSetToTransaction(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return transactions;
    }

    @Override
    public List<Transaction> findAllByCategoryId(Long categoryId) {
        return entityManager.
                createQuery("select t from Transaction t where t.category.id = :categoryId", Transaction.class).
                setParameter("categoryId", categoryId).
                getResultList();
    }

    @Override
    public List<Transaction> findAllByUserId(Long userId) {
        return entityManager.
                createQuery("select t from Transaction t inner join Account a on t.account.id = a.id where a.user.id = :userId", Transaction.class).
                setParameter("userId", userId).
                getResultList();
    }

    private Transaction convertResultSetToTransaction(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Timestamp created = resultSet.getTimestamp("created");
        Long amount = resultSet.getLong("amount");
        Long accountId = resultSet.getLong("account_id");
        Long categoryId = resultSet.getLong("category_id");
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setAmount(amount);
        transaction.setCreated(new Date(created.getTime()));
        transaction.setAccount(accountDao.findById(accountId));
        transaction.setCategory(categoryDao.findById(categoryId));
        return transaction;
    }
}
