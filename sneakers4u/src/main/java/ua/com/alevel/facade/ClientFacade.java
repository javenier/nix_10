package ua.com.alevel.facade;

import ua.com.alevel.view.dto.user.ClientRequestDto;
import ua.com.alevel.view.dto.user.ClientResponseDto;

public interface ClientFacade extends BaseFacade<ClientRequestDto, ClientResponseDto> {

    ClientResponseDto findByEmail(String email);

    void disableClient(Long id);

    void enableClient(Long id);
}
