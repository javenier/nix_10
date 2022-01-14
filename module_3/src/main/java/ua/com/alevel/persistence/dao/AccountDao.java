package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.entity.Account;

import java.util.List;

public interface AccountDao extends BaseDao<Account> {

    List<Account> findAllByUserId(Long userId);
}
