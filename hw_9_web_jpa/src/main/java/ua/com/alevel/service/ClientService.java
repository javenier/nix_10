package ua.com.alevel.service;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Client;

public interface ClientService extends BaseService<Client> {

    void create(Client client, Long bankId);

    DataTableResponse<Client> findAllByBankId(DataTableRequest request, Long bankId);
}
