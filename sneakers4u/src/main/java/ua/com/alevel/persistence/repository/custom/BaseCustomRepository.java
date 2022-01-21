package ua.com.alevel.persistence.repository.custom;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;

public interface BaseCustomRepository<E extends BaseEntity> {

    DataTableResponse<E> findAll(DataTableRequest request);
    void deleteById(Long id);
}
