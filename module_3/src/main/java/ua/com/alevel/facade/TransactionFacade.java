package ua.com.alevel.facade;

import ua.com.alevel.view.dto.transaction.TransactionRequestDto;
import ua.com.alevel.view.dto.transaction.TransactionResponseDto;

import java.util.List;

public interface TransactionFacade extends BaseFacade<TransactionRequestDto, TransactionResponseDto> {

    List<TransactionResponseDto> findAllByAccountId(Long accountId);
    List<TransactionResponseDto> findAllByCategoryId(Long categoryId);
    List<TransactionResponseDto> findAllByUserId(Long userId);
    void exportAccountStatement(Long accountId);
}
