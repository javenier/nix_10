package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.entity.item.Sneaker;
import ua.com.alevel.persistence.repository.custom.SneakerCustomRepository;
import ua.com.alevel.persistence.repository.SneakerRepository;
import ua.com.alevel.service.SneakerService;

import java.util.Optional;

@Service
public class SneakerServiceImpl implements SneakerService {

    private final SneakerRepository sneakerRepository;
    private final SneakerCustomRepository sneakerCustomRepository;

    public SneakerServiceImpl(SneakerRepository sneakerRepository, SneakerCustomRepository sneakerCustomRepository) {
        this.sneakerRepository = sneakerRepository;
        this.sneakerCustomRepository = sneakerCustomRepository;
    }

    @Override
    public void create(Sneaker entity) {
        sneakerRepository.save(entity);
    }

    @Override
    public void update(Sneaker entity) {
        if(!sneakerRepository.existsById(entity.getId())) {
            throw new EntityNotFoundException("not found...");
        }
        sneakerRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if(!sneakerRepository.existsById(id)) {
            throw new EntityNotFoundException("not found...");
        }
        sneakerCustomRepository.deleteById(id);
    }

    @Override
    public Sneaker findById(Long id) {
        Optional<Sneaker> sneaker = sneakerRepository.findById(id);
        if(sneaker.isPresent())
            return sneaker.get();
        else
            throw new EntityNotFoundException("not found...");
    }

    @Override
    public DataTableResponse<Sneaker> findAll(DataTableRequest request) {
        DataTableResponse<Sneaker> dataTableResponse = sneakerCustomRepository.findAll(request);
        long count = sneakerRepository.count();
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    @Override
    public DataTableResponse<Sneaker> findAllByBrandId(DataTableRequest request, Long brandId) {
        DataTableResponse<Sneaker> dataTableResponse = sneakerCustomRepository.findAllByBrandId(request, brandId);
        long count = sneakerRepository.count();
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    @Override
    public DataTableResponse<Sneaker> findAllByModelId(DataTableRequest request, Long modelId) {
        DataTableResponse<Sneaker> dataTableResponse = sneakerCustomRepository.findAllByModelId(request, modelId);
        long count = sneakerRepository.count();
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    @Override
    public DataTableResponse<Sneaker> findAllByOrderId(DataTableRequest request, Long orderId) {
        DataTableResponse<Sneaker> dataTableResponse = sneakerCustomRepository.findAllByOrderId(request, orderId);
        long count = sneakerRepository.count();
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    @Override
    public DataTableResponse<Sneaker> findAllByGender(DataTableRequest request, String gender) {
        DataTableResponse<Sneaker> dataTableResponse = sneakerCustomRepository.findAllByGender(request, gender);
        long count = sneakerRepository.count();
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }
}
