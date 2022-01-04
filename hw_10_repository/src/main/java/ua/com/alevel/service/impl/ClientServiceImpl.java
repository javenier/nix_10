package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.exception.EntityNotFoundException;
import ua.com.alevel.crud.CrudRepositoryHelper;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Bank;
import ua.com.alevel.entity.Client;
import ua.com.alevel.repository.BankRepository;
import ua.com.alevel.repository.ClientCustomRepository;
import ua.com.alevel.repository.ClientRepository;
import ua.com.alevel.service.ClientService;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final CrudRepositoryHelper<Client, ClientRepository> crudRepositoryHelper;
    private final ClientRepository clientRepository;
    private final BankRepository bankRepository;
    private final ClientCustomRepository clientCustomRepository;

    public ClientServiceImpl(CrudRepositoryHelper<Client, ClientRepository> crudRepositoryHelper, ClientRepository clientRepository, BankRepository bankRepository, ClientCustomRepository clientCustomRepository) {
        this.crudRepositoryHelper = crudRepositoryHelper;
        this.clientRepository = clientRepository;
        this.bankRepository = bankRepository;
        this.clientCustomRepository = clientCustomRepository;
    }

    @Override
    public void create(Client entity, Long bankId) {
        Bank bank = bankRepository.getById(bankId);
        bank.addClient(entity);
        bankRepository.save(bank);
    }

    @Override
    public void update(Client entity) {
        if(crudRepositoryHelper.existById(clientRepository, entity.getId()))
            crudRepositoryHelper.update(clientRepository, entity);
    }

    @Override
    public void delete(Long id) {
        if(crudRepositoryHelper.existById(clientRepository, id))
            clientCustomRepository.deleteById(id);
        else
            throw new EntityNotFoundException("not found...");
    }

    @Override
    public Client findById(Long id) {
        Optional<Client> client = crudRepositoryHelper.findById(clientRepository, id);
        if(client.isPresent())
            return client.get();
        else
            throw new EntityNotFoundException("not found...");
    }

    @Override
    public DataTableResponse<Client> findAll(DataTableRequest request) {
        DataTableResponse<Client> dataTableResponse = clientCustomRepository.findAll(request);
        long count = crudRepositoryHelper.count(clientRepository);
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    @Override
    public DataTableResponse<Client> findAllByBankId(DataTableRequest request, Long bankId) {
        DataTableResponse<Client> dataTableResponse = clientCustomRepository.findAllByBankId(request, bankId);
        long count = crudRepositoryHelper.count(clientRepository);
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }
}