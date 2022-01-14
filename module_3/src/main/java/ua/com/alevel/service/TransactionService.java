package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.Transaction;

import java.util.List;

public interface TransactionService extends BaseService<Transaction> {

    List<Transaction> findAllByAccountId(Long accountId);
    List<Transaction> findAllByCategoryId(Long categoryId);
    List<Transaction> findAllByUserId(Long userId);
    void exportAccountStatement(Long accountId);
}
