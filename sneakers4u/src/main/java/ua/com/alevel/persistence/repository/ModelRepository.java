package ua.com.alevel.persistence.repository;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.item.attributes.Model;

@Repository
public interface ModelRepository extends BaseRepository<Model> {

    Model findByName(String name);
}
