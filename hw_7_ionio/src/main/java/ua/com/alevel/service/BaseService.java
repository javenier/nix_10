package ua.com.alevel.service;

import ua.com.alevel.entity.BaseEntity;

import java.util.List;

public interface BaseService<E extends BaseEntity> {

    void create(E entity);

    void update(E entity);

    void delete(String id);

    boolean existsById(String id);

    E findById(String id);

    List<E> findAll();
}