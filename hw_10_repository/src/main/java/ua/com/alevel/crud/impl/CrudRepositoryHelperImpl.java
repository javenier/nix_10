package ua.com.alevel.crud.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.alevel.config.exception.EntityNotFoundException;
import ua.com.alevel.crud.CrudRepositoryHelper;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.entity.BaseEntity;
import ua.com.alevel.repository.BaseRepository;

import java.util.Optional;

@Service
public class CrudRepositoryHelperImpl<E extends BaseEntity,
        R extends BaseRepository<E>>
        implements CrudRepositoryHelper<E, R> {

    @Override
    public void create(R repository, E entity) {
        repository.save(entity);
    }

    @Override
    public void update(R repository, E entity) {
        checkById(repository, entity.getId());
        repository.save(entity);
    }

    @Override
    public boolean existById(R repository, Long id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<E> findById(R repository, Long id) {
        return repository.findById(id);
    }

    @Override
    public long count(R repository) {
        return repository.count();
    }

    private void checkById(R repository, Long id) {
        if(!repository.existsById(id))
            throw new EntityNotFoundException("not found...");
    }
}
