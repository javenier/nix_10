package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.dto.LinkRequestDto;
import ua.com.alevel.dto.PageData;
import ua.com.alevel.dto.bank.BankRequestDto;
import ua.com.alevel.dto.bank.BankResponseDto;


public interface BankFacade extends BaseFacade<BankRequestDto, BankResponseDto> {

    PageData<BankResponseDto> findAllByClientId(WebRequest request, Long clientId);

    void link(LinkRequestDto dto);

    void unlink(LinkRequestDto dto);
}
