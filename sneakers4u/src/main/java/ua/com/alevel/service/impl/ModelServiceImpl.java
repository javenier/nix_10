package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.entity.item.attributes.Model;
import ua.com.alevel.persistence.repository.ModelRepository;
import ua.com.alevel.persistence.repository.custom.ModelCustomRepository;
import ua.com.alevel.service.ModelService;

import java.util.Optional;

@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;
    private final ModelCustomRepository modelCustomRepository;

    public ModelServiceImpl(ModelRepository modelRepository, ModelCustomRepository modelCustomRepository) {
        this.modelRepository = modelRepository;
        this.modelCustomRepository = modelCustomRepository;
    }

    @Override
    public void create(Model entity) {
        modelRepository.save(entity);
    }

    @Override
    public void update(Model entity) {
        if(!modelRepository.existsById(entity.getId())) {
            throw new EntityNotFoundException("not found...");
        }
        modelRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if(!modelRepository.existsById(id)) {
            throw new EntityNotFoundException("not found...");
        }
        modelCustomRepository.deleteById(id);
    }

    @Override
    public Model findById(Long id) {
        Optional<Model> model = modelRepository.findById(id);
        if(model.isPresent())
            return model.get();
        else
            throw new EntityNotFoundException("not found...");
    }

    @Override
    public DataTableResponse<Model> findAll(DataTableRequest request) {
        DataTableResponse<Model> dataTableResponse = modelCustomRepository.findAll(request);
        long count = modelRepository.count();
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    @Override
    public DataTableResponse<Model> findAllByBrandId(DataTableRequest request, Long brandId) {
        DataTableResponse<Model> dataTableResponse = modelCustomRepository.findAllByBrandId(request, brandId);
        long count = modelRepository.count();
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }
}
