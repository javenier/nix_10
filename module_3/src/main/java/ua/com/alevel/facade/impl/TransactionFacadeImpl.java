package ua.com.alevel.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.com.alevel.facade.TransactionFacade;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.Category;
import ua.com.alevel.persistence.entity.Transaction;
import ua.com.alevel.service.AccountService;
import ua.com.alevel.service.CategoryService;
import ua.com.alevel.service.TransactionService;
import ua.com.alevel.util.MoneyConverterUtil;
import ua.com.alevel.view.dto.transaction.TransactionRequestDto;
import ua.com.alevel.view.dto.transaction.TransactionResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionFacadeImpl implements TransactionFacade {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final CategoryService categoryService;

    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");

    public TransactionFacadeImpl(TransactionService transactionService, AccountService accountService, CategoryService categoryService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    @Override
    public void create(TransactionRequestDto transactionRequestDto) {
        Transaction transaction = new Transaction();
        Account account = accountService.findById(transactionRequestDto.getAccountId());
        Category category = categoryService.findById(transactionRequestDto.getCategoryId());
        Long transactionSum = MoneyConverterUtil.stringToPenny(transactionRequestDto.getAmount());
        if(transactionSum <= 0) {
            LOGGER_WARN.warn("Failed transaction attempt. Transaction sum must be greater than 0.");
            throw new RuntimeException("Transaction sum must be greater than 0.");
        }
        if(!category.isIncome() && (transactionSum > account.getBalance())) {
            LOGGER_WARN.warn("Failed transaction attempt. User doesn't have enough money.");
            throw new RuntimeException("You don't have enough money.");
        }
        transaction.setAmount(transactionSum);
        account.addTransaction(transaction);
        category.addTransaction(transaction);
        transactionService.create(transaction);
    }

    @Override
    public void update(TransactionRequestDto transactionRequestDto) {
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public TransactionResponseDto findById(Long id) {
        return new TransactionResponseDto(transactionService.findById(id));
    }

    @Override
    public List<TransactionResponseDto> findAll() {
        List<Transaction> transactions = transactionService.findAll();
        return generateDtoListByEntityList(transactions);
    }

    @Override
    public List<TransactionResponseDto> findAllByAccountId(Long accountId) {
        List<Transaction> transactions = transactionService.findAllByAccountId(accountId);
        return generateDtoListByEntityList(transactions);
    }

    @Override
    public List<TransactionResponseDto> findAllByCategoryId(Long categoryId) {
        List<Transaction> transactions = transactionService.findAllByCategoryId(categoryId);
        return generateDtoListByEntityList(transactions);
    }

    @Override
    public List<TransactionResponseDto> findAllByUserId(Long userId) {
        List<Transaction> transactions = transactionService.findAllByUserId(userId);
        return generateDtoListByEntityList(transactions);
    }

    @Override
    public void exportAccountStatement(Long accountId) {
        transactionService.exportAccountStatement(accountId);
    }

    List<TransactionResponseDto> generateDtoListByEntityList(List<Transaction> transactions) {
        return transactions.
                stream().
                map(TransactionResponseDto::new).
                collect(Collectors.toList());
    }
}