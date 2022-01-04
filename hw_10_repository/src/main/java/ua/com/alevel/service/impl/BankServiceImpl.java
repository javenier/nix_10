package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.exception.EntityNotFoundException;
import ua.com.alevel.crud.CrudRepositoryHelper;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Bank;
import ua.com.alevel.entity.Client;
import ua.com.alevel.repository.BankCustomRepository;
import ua.com.alevel.repository.BankRepository;
import ua.com.alevel.repository.ClientRepository;
import ua.com.alevel.service.BankService;

import java.util.Optional;

@Service
public class BankServiceImpl implements BankService {

    private final CrudRepositoryHelper<Bank, BankRepository> crudRepositoryHelper;
    private final BankRepository bankRepository;
    private final ClientRepository clientRepository;
    private final BankCustomRepository bankCustomRepository;

    public BankServiceImpl(CrudRepositoryHelper<Bank, BankRepository> crudRepositoryHelper, BankRepository bankRepository, ClientRepository clientRepository, BankCustomRepository bankCustomRepository) {
        this.crudRepositoryHelper = crudRepositoryHelper;
        this.bankRepository = bankRepository;
        this.clientRepository = clientRepository;
        this.bankCustomRepository = bankCustomRepository;
    }

    @Override
    public DataTableResponse<Bank> findAllByClientId(DataTableRequest request, Long clientId) {
        DataTableResponse<Bank> dataTableResponse = bankCustomRepository.findAllByClientId(request, clientId);
        long count = crudRepositoryHelper.count(bankRepository);
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    @Override
    public void link(Long bankId, Long clientId) {
        Client client = clientRepository.getById(clientId);
        Bank bank = findById(bankId);
        bank.addClient(client);
        update(bank);
    }

    @Override
    public void unlink(Long bankId, Long clientId) {
        Client client = clientRepository.getById(clientId);
        Bank bank = findById(bankId);
        bank.removeClient(client);
        update(bank);
    }

    @Override
    public void create(Bank entity) {
        crudRepositoryHelper.create(bankRepository, entity);
    }

    @Override
    public void update(Bank entity) {
        if(crudRepositoryHelper.existById(bankRepository, entity.getId()))
            crudRepositoryHelper.update(bankRepository, entity);
    }

    @Override
    public void delete(Long id) {
        if(crudRepositoryHelper.existById(bankRepository, id)) {
            bankCustomRepository.deleteById(id);
        }
    }

    @Override
    public Bank findById(Long id) {
        Optional<Bank> bank = crudRepositoryHelper.findById(bankRepository, id);
        if(bank.isPresent())
            return bank.get();
        else
            throw new EntityNotFoundException("not found...");
    }

    @Override
    public DataTableResponse<Bank> findAll(DataTableRequest request) {
        DataTableResponse<Bank> dataTableResponse = bankCustomRepository.findAll(request);
        long count = crudRepositoryHelper.count(bankRepository);
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }
}
