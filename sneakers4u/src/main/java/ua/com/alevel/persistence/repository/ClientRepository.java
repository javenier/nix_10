package ua.com.alevel.persistence.repository;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.user.Client;

@Repository
public interface ClientRepository extends UserRepository<Client> {

    Client findByActivationCode(String code);
}
