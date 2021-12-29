package ua.com.alevel.service;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Bank;

import java.util.List;

public interface BankService extends BaseService<Bank> {

    void create(Bank bank);

    DataTableResponse<Bank> findAllByClientId(DataTableRequest request, Long clientId);

    void link(Long bankId, Long clientId);

    void unlink(Long bankId, Long clientId);
}
