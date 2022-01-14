package ua.com.alevel.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.AccountDao;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.service.AccountService;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;

    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void create(Account entity) {
        accountDao.create(entity);
        LOGGER_INFO.info("Account with id = " + entity.getId() + " has been created");
    }

    @Override
    public void update(Account entity) {
        if(accountDao.existById(entity.getId())) {
            accountDao.update(entity);
            LOGGER_INFO.info("Account with id = " + entity.getId() + " has been updated");
        }
        else {
            LOGGER_WARN.warn("Account with id = " + entity.getId() + " has not been found");
            throw new RuntimeException("not found...");
        }
    }

    @Override
    public void delete(Long id) {
        if(accountDao.existById(id)) {
            accountDao.delete(id);
            LOGGER_INFO.info("Account with id = " + id + " has been deleted");
        }
        else {
            LOGGER_WARN.warn("Account with id = " + id + " has not been found");
            throw new RuntimeException("not found...");
        }
    }

    @Override
    public Account findById(Long id) {
        Account account = accountDao.findById(id);
        if(account == null) {
            LOGGER_WARN.warn("Account with id = " + id + " has not been found");
            throw new RuntimeException("not found...");
        }
        LOGGER_INFO.info("Account with id = " + id + " has been found");
        return account;
    }

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public List<Account> findAllByUserId(Long userId) {
        return accountDao.findAllByUserId(userId);
    }
}
