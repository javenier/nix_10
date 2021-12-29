package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.dto.PageData;
import ua.com.alevel.dto.client.ClientRequestDto;
import ua.com.alevel.dto.client.ClientResponseDto;


public interface ClientFacade extends BaseFacade<ClientRequestDto, ClientResponseDto> {

    PageData<ClientResponseDto> findAllByBankId(WebRequest request, Long bankId);
}
