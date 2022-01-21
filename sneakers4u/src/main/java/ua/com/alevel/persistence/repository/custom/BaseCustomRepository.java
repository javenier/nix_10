package ua.com.alevel.persistence.repository.custom;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;

import java.util.List;

public interface BaseCustomRepository<E extends BaseEntity> {

    DataTableResponse<E> findAll(DataTableRequest request);
    void deleteById(Long id);
}
