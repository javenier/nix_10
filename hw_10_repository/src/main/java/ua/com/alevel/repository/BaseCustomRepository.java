package ua.com.alevel.repository;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.BaseEntity;

public interface BaseCustomRepository<E extends BaseEntity> {

    DataTableResponse<E> findAll(DataTableRequest request);
    void deleteById(Long id);
}
