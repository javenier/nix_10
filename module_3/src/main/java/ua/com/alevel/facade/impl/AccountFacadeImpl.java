package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.facade.AccountFacade;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.service.AccountService;
import ua.com.alevel.service.UserService;
import ua.com.alevel.view.dto.account.AccountRequestDto;
import ua.com.alevel.view.dto.account.AccountResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountFacadeImpl implements AccountFacade {

    private final AccountService accountService;
    private final UserService userService;

    public AccountFacadeImpl(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void create(AccountRequestDto accountRequestDto) {
        Account account = new Account();
        User user = userService.findById(accountRequestDto.getUserId());
        account.setAccountType(accountRequestDto.getAccountType());
        user.addAccount(account);
        userService.update(user);
    }

    @Override
    public void update(AccountRequestDto accountRequestDto) {
        Account account = accountService.findById(accountRequestDto.getId());
        if(account != null) {
            account.setAccountType(accountRequestDto.getAccountType());
            accountService.update(account);
        }
    }

    @Override
    public void delete(Long id) {
        accountService.delete(id);
    }

    @Override
    public AccountResponseDto findById(Long id) {
        return new AccountResponseDto(accountService.findById(id));
    }

    @Override
    public List<AccountResponseDto> findAll() {
        List<Account> accounts = accountService.findAll();
        return accounts.
                stream().
                map(AccountResponseDto::new).
                collect(Collectors.toList());
    }

    @Override
    public List<AccountResponseDto> findAllByUserId(Long userId) {
        List<Account> accounts = accountService.findAllByUserId(userId);
        return accounts.
                stream().
                map(AccountResponseDto::new).
                collect(Collectors.toList());
    }
}
