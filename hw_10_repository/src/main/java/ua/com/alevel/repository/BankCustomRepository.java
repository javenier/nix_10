package ua.com.alevel.repository;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.Bank;

public interface BankCustomRepository extends BaseCustomRepository<Bank> {

    DataTableResponse<Bank> findAllByClientId(DataTableRequest request, Long clientId);
}
