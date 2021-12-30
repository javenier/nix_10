package ua.com.alevel.dao;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Bank;
import ua.com.alevel.entity.Client;

public interface BankDao extends BaseDao<Bank> {

    void create(Bank bank);

    DataTableResponse<Bank> findAllByClientId(DataTableRequest request, Long clientId);

    void link(Long bankId, Client client);

    void unlink(Long bankId, Client client);
}
