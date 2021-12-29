package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.exception.EntityNotFoundException;
import ua.com.alevel.dao.BankDao;
import ua.com.alevel.dao.ClientDao;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Client;
import ua.com.alevel.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;
    private final BankDao bankDao;

    public ClientServiceImpl(ClientDao clientDao, BankDao bankDao) {
        this.clientDao = clientDao;
        this.bankDao = bankDao;
    }

    @Override
    public void create(Client entity, Long bankId) {
        clientDao.create(entity, bankId);
    }

    @Override
    public void update(Client entity) {
        if(clientDao.existById(entity.getId()))
            clientDao.update(entity);
    }

    @Override
    public void delete(Long id) {
        clientDao.delete(id);
    }

    @Override
    public Client findById(Long id) {
        Client client = clientDao.findById(id);
        if(client == null)
            throw new EntityNotFoundException("not found...");
        return client;
    }

    @Override
    public DataTableResponse<Client> findAll(DataTableRequest request) {
        DataTableResponse<Client> dataTableResponse = clientDao.findAll(request);
        long count = clientDao.count();
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    @Override
    public DataTableResponse<Client> findAllByBankId(DataTableRequest request, Long bankId) {
        DataTableResponse<Client> dataTableResponse = clientDao.findAllByBankId(request, bankId);
        long count = clientDao.count();
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }
}