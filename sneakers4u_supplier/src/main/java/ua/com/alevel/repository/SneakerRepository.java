package ua.com.alevel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.alevel.entity.Sneaker;

@Repository
public interface SneakerRepository extends JpaRepository<Sneaker, Long> {
}
