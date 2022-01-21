package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.entity.item.Sneaker;
import ua.com.alevel.persistence.entity.item.attributes.Size;
import ua.com.alevel.persistence.repository.ModelRepository;
import ua.com.alevel.persistence.repository.SizeRepository;
import ua.com.alevel.persistence.repository.SneakerRepository;
import ua.com.alevel.persistence.repository.custom.SizeCustomRepository;
import ua.com.alevel.service.SizeService;

import java.util.Optional;

@Service
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;
    private final SizeCustomRepository sizeCustomRepository;
    private final SneakerRepository sneakerRepository;

    public SizeServiceImpl(SizeRepository sizeRepository, SizeCustomRepository sizeCustomRepository, ModelRepository modelRepository, SneakerRepository sneakerRepository) {
        this.sizeRepository = sizeRepository;
        this.sizeCustomRepository = sizeCustomRepository;
        this.sneakerRepository = sneakerRepository;
    }

    @Override
    public void create(Size entity) {
        sizeRepository.save(entity);
    }

    @Override
    public void update(Size entity) {
        if(!sizeRepository.existsById(entity.getId())) {
            throw new EntityNotFoundException("not found...");
        }
        sizeRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if(!sizeRepository.existsById(id)) {
            throw new EntityNotFoundException("not found...");
        }
        sizeCustomRepository.deleteById(id);
    }

    @Override
    public Size findById(Long id) {
        Optional<Size> size = sizeRepository.findById(id);
        if(size.isPresent())
            return size.get();
        else
            throw new EntityNotFoundException("not found...");
    }

    @Override
    public DataTableResponse<Size> findAll(DataTableRequest request) {
        DataTableResponse<Size> dataTableResponse = sizeCustomRepository.findAll(request);
        long count = sizeRepository.count();
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }

    @Override
    public void link(Long sneakerId, Long sizeId) {
        Sneaker sneaker = sneakerRepository.getById(sneakerId);
        Size size = findById(sizeId);
        sneaker.addSize(size);
        update(size);
    }

    @Override
    public void unlink(Long sneakerId, Long sizeId) {
        Sneaker sneaker = sneakerRepository.getById(sneakerId);
        Size size = findById(sizeId);
        sneaker.removeSize(size);
        sneakerRepository.save(sneaker);
    }

    @Override
    public DataTableResponse<Size> findAllBySneakerId(DataTableRequest request, Long sneakerId) {
        DataTableResponse<Size> dataTableResponse = sizeCustomRepository.findAllBySneakerId(request, sneakerId);
        long count = sizeRepository.count();
        dataTableResponse.setItemsSize(count);
        return dataTableResponse;
    }
}
