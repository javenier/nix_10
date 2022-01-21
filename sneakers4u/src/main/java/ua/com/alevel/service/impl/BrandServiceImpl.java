package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.entity.item.attributes.Brand;
import ua.com.alevel.persistence.entity.item.attributes.Model;
import ua.com.alevel.persistence.repository.BrandRepository;
import ua.com.alevel.persistence.repository.custom.BrandCustomRepository;
import ua.com.alevel.service.BrandService;

import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandCustomRepository brandCustomRepository;

    public BrandServiceImpl(BrandRepository brandRepository, BrandCustomRepository brandCustomRepository) {
        this.brandRepository = brandRepository;
        this.brandCustomRepository = brandCustomRepository;
    }

    @Override
    public void create(Brand entity) {
        brandRepository.save(entity);
    }

    @Override
    public void update(Brand entity) {
        if(!brandRepository.existsById(entity.getId())) {
            throw new EntityNotFoundException("not found...");
        }
        brandRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if(!brandRepository.existsById(id)) {
            throw new EntityNotFoundException("not found...");
        }
        brandCustomRepository.deleteById(id);
    }

    @Override
    public Brand findById(Long id) {
        Optional<Brand> brand = brandRepository.findById(id);
        if(brand.isPresent())
            return brand.get();
        else
            throw new EntityNotFoundException("not found...");
    }

    @Override
    public DataTableResponse<Brand> findAll(DataTableRequest request) {
        DataTableResponse<Brand> dataTableResponse = brandCustomRepository.findAll(request);
        long count = brandRepository.count();
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }
}
