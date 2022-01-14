package ua.com.alevel.facade;

import ua.com.alevel.view.dto.account.AccountRequestDto;
import ua.com.alevel.view.dto.account.AccountResponseDto;

import java.util.List;

public interface AccountFacade extends BaseFacade<AccountRequestDto, AccountResponseDto> {

    List<AccountResponseDto> findAllByUserId(Long userId);
}
