package ua.com.alevel.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.entity.user.Client;
import ua.com.alevel.persistence.repository.ClientRepository;
import ua.com.alevel.persistence.repository.custom.ClientCustomRepository;
import ua.com.alevel.service.ClientService;

import javax.persistence.EntityExistsException;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final ClientCustomRepository clientCustomRepository;

    public ClientServiceImpl(BCryptPasswordEncoder passwordEncoder, ClientRepository clientRepository, ClientCustomRepository clientCustomRepository) {
        this.passwordEncoder = passwordEncoder;
        this.clientRepository = clientRepository;
        this.clientCustomRepository = clientCustomRepository;
    }

    @Override
    public void create(Client entity) {
        if(clientRepository.existsByEmail(entity.getEmail())) {
            throw new EntityExistsException("this client exists");
        }
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        clientRepository.save(entity);
    }

    @Override
    public void update(Client entity) {
        if(!clientRepository.existsById(entity.getId())) {
            throw new EntityNotFoundException("not found...");
        }
        clientRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if(!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("not found...");
        }
        clientCustomRepository.deleteById(id);
    }

    @Override
    public Client findById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent())
            return client.get();
        else
            throw new EntityNotFoundException("not found...");
    }

    @Override
    public DataTableResponse<Client> findAll(DataTableRequest request) {
        DataTableResponse<Client> dataTableResponse = clientCustomRepository.findAll(request);
        long count = clientRepository.count();
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public void disableClient(Long id) {
        if(!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("not found...");
        }
        clientCustomRepository.disableClient(id);
    }

    @Override
    public void enableClient(Long id) {
        if(!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("not found...");
        }
        clientCustomRepository.enableClient(id);
    }

    @Override
    public Long findUnfinishedOrderId(Long clientId) {
        return clientCustomRepository.findUnfinishedOrderId(clientId);
    }
}
