package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.entity.Transaction;

import java.util.List;

public interface TransactionDao extends BaseDao<Transaction> {

    List<Transaction> findAllByAccountId(Long accountId);
    List<Transaction> findAllByAccountIdJdbc(Long accountId);
    List<Transaction> findAllByCategoryId(Long categoryId);
    List<Transaction> findAllByUserId(Long userId);
}
