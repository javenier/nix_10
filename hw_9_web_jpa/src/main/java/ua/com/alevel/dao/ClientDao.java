package ua.com.alevel.dao;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Client;

public interface ClientDao extends BaseDao<Client> {

    void create(Client client, Long bankId);

    DataTableResponse<Client> findAllByBankId(DataTableRequest request, Long bankId);
}
