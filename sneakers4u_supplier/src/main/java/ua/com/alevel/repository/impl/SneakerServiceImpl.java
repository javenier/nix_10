package ua.com.alevel.repository.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.entity.Sneaker;
import ua.com.alevel.repository.SneakerRepository;
import ua.com.alevel.service.SneakerService;

import java.util.List;

@Service
public class SneakerServiceImpl implements SneakerService {

    private final SneakerRepository sneakerRepository;

    public SneakerServiceImpl(SneakerRepository sneakerRepository) {
        this.sneakerRepository = sneakerRepository;
    }

    @Override
    public List<Sneaker> syncToSupplier() {
        return sneakerRepository.findAll();
    }
}
