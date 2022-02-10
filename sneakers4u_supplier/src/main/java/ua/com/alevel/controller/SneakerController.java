package ua.com.alevel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.alevel.entity.Sneaker;
import ua.com.alevel.service.SneakerService;

import java.util.List;

@RestController
@RequestMapping("/api/sneakers")
public class SneakerController {

    private final SneakerService sneakerService;

    public SneakerController(SneakerService sneakerService) {
        this.sneakerService = sneakerService;
    }

    @GetMapping
    public List<Sneaker> findAll() {
        return sneakerService.syncToSupplier();
    }
}
