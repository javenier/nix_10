package ua.com.alevel.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.com.alevel.cron.model.SneakerSupplier;
import ua.com.alevel.persistence.entity.item.Sneaker;
import ua.com.alevel.persistence.repository.SneakerRepository;
import ua.com.alevel.service.SupplierService;

import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Value("${supplier.url}")
    private String url;

    @Value("${supplier.token}")
    private String token;

    private final SneakerRepository sneakerRepository;

    public SupplierServiceImpl(SneakerRepository sneakerRepository) {
        this.sneakerRepository = sneakerRepository;
    }

    @Override
    public void syncToSupplier() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("x_auth_token", token);
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<SneakerSupplier[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                SneakerSupplier[].class
        );
        if(response.getStatusCodeValue() == 200) {
            SneakerSupplier[] sneakerSuppliers = response.getBody();
            if(sneakerSuppliers != null) {
                for (SneakerSupplier sneakerSupplier : sneakerSuppliers) {
                    Optional<Sneaker> sneakerOptional = sneakerRepository.findById(sneakerSupplier.getSneakerId());
                    if(sneakerOptional.isPresent()) {
                        Sneaker sneaker = sneakerOptional.get();
                        sneaker.setQuantity(sneakerSupplier.getQuantity());
                        sneakerRepository.save(sneaker);
                    }
                }
            }
        }
    }
}
