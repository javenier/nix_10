package ua.com.alevel.service.impl;

import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.AccountDao;
import ua.com.alevel.persistence.dao.TransactionDao;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.Transaction;
import ua.com.alevel.service.TransactionService;
import ua.com.alevel.util.MoneyConverterUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionDao transactionDao;
    private final AccountDao accountDao;

    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");

    public TransactionServiceImpl(TransactionDao transactionDao, AccountDao accountDao) {
        this.transactionDao = transactionDao;
        this.accountDao = accountDao;
    }

    @Override
    public void create(Transaction entity) {
        transactionDao.create(entity);
        updateAccountBalance(entity);
        LOGGER_INFO.info("Transaction with id = " + entity.getId() + " has been created");
    }

    @Override
    public void update(Transaction entity) {
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public Transaction findById(Long id) {
        Transaction transaction = transactionDao.findById(id);
        if(transaction == null) {
            LOGGER_WARN.warn("Transaction with id = " + id + " has not been found");
            throw new RuntimeException("not found...");
        }
        LOGGER_INFO.info("Transaction with id = " + id + " has been found");
        return transaction;
    }

    @Override
    public List<Transaction> findAll() {
        return transactionDao.findAll();
    }

    @Override
    public List<Transaction> findAllByAccountId(Long accountId) {
        return transactionDao.findAllByAccountId(accountId);
    }

    @Override
    public List<Transaction> findAllByCategoryId(Long categoryId) {
        return transactionDao.findAllByCategoryId(categoryId);
    }

    @Override
    public List<Transaction> findAllByUserId(Long userId) {
        return transactionDao.findAllByUserId(userId);
    }

    @Override
    public void exportAccountStatement(Long accountId) {
        List<String[]> statement = new ArrayList<>();
        String[] header = { "Id", "Amount", "Created", "Category", "Associate" };
        statement.add(header);

        List<Transaction> transactions = transactionDao.findAllByAccountIdJdbc(accountId);

        for(int i = 0; i < transactions.size(); i++) {
            String[] transactionString = new String[5];
            transactionString[0] = String.valueOf(transactions.get(i).getId());
            transactionString[1] = MoneyConverterUtil.pennyToString(transactions.get(i).getAmount());
            transactionString[2] = transactions.get(i).getCreated().toString();
            transactionString[3] = transactions.get(i).getCategory().getName();
            transactionString[4] = transactions.get(i).getCategory().isIncome() ? "Income" : "Outcome";
            statement.add(transactionString);
        }

        try(CSVWriter csvWriter = new CSVWriter(new FileWriter("account" + accountId + ".csv"))) {
            csvWriter.writeAll(statement);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER_INFO.info("Account statement for account with id = " + accountId + " has been successfully exported");
    }

    private void updateAccountBalance(Transaction transaction) {
        Account account = accountDao.findById(transaction.getAccount().getId());
        long currentBalance = account.getBalance();
        if(transaction.getCategory().isIncome())
            currentBalance += transaction.getAmount();
        else
            currentBalance -= transaction.getAmount();
        account.setBalance(currentBalance);
        accountDao.update(account);
    }
}
