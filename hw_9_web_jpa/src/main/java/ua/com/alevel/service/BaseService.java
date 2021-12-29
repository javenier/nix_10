package ua.com.alevel.service;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.BaseEntity;

public interface BaseService<E extends BaseEntity> {

    void update(E entity);

    void delete(Long id);

    E findById(Long id);

    DataTableResponse<E> findAll(DataTableRequest request);
}
