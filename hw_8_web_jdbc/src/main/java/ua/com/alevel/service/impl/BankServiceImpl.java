package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.config.exceptions.EntityNotFoundException;
import ua.com.alevel.dao.BankDao;
import ua.com.alevel.dao.ClientDao;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Bank;
import ua.com.alevel.service.BankService;

import java.util.List;

@Service
public class BankServiceImpl implements BankService {

    private final BankDao bankDao;
    private final ClientDao clientDao;

    public BankServiceImpl(BankDao bankDao, ClientDao clientDao) {
        this.bankDao = bankDao;
        this.clientDao = clientDao;
    }

    @Override
    public DataTableResponse<Bank> findAllByClientId(DataTableRequest request, Long clientId) {
        DataTableResponse<Bank> dataTableResponse = bankDao.findAllByClientId(request, clientId);
        long count = bankDao.count();
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    @Override
    public void link(Long bankId, Long clientId) {
        bankDao.link(bankId, clientId);
    }

    @Override
    public void unlink(Long bankId, Long clientId) {
        bankDao.unlink(bankId, clientId);
    }

    @Override
    public void create(Bank entity) {
        bankDao.create(entity);
    }

    @Override
    public void update(Bank entity) {
        if(bankDao.existById(entity.getId()))
            bankDao.update(entity);
    }

    ////////////??????
    @Override
    public void delete(Long id) {
        if(bankDao.existById(id)) {
            bankDao.delete(id);
        }
    }

    @Override
    public Bank findById(Long id) {
        Bank bank = bankDao.findById(id);
        if(bank == null) {
            throw new EntityNotFoundException("not found...");
        }
        return bank;
    }

    @Override
    public DataTableResponse<Bank> findAll(DataTableRequest request) {
        DataTableResponse<Bank> dataTableResponse = bankDao.findAll(request);
        long count = bankDao.count();
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }
}
