package ua.com.alevel.persistence.repository;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.item.Sneaker;
import ua.com.alevel.persistence.type.Gender;

@Repository
public interface SneakerRepository extends BaseRepository<Sneaker> {

    long countBySneakerGender(Gender gender);
    long countByModelId(Long modelId);
    long countByFullNameContaining(String query);
    Sneaker findSneakerByVersionOfModel(String version);
}
