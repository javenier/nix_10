package ua.com.alevel.repository;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Client;

public interface ClientCustomRepository extends BaseCustomRepository<Client> {

    DataTableResponse<Client> findAllByBankId(DataTableRequest request, Long bankId);
}
