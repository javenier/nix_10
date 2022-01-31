package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.facade.RegistrationFacade;
import ua.com.alevel.persistence.entity.user.Client;
import ua.com.alevel.service.ClientService;
import ua.com.alevel.view.dto.auth.AuthDto;

@Service
public class RegistrationFacadeImpl implements RegistrationFacade {

    private final ClientService clientService;

    public RegistrationFacadeImpl(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void registration(AuthDto dto) {
        Client client = new Client();
        client.setEmail(dto.getEmail());
        client.setPassword(dto.getPassword());
        clientService.create(client);
    }
}
