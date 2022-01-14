package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.Account;

import java.util.List;

public interface AccountService extends BaseService<Account> {

    List<Account> findAllByUserId(Long userId);
}
