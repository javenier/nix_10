package ua.com.alevel.persistence.repository.custom;

import ua.com.alevel.persistence.entity.order.Order;
import ua.com.alevel.persistence.entity.user.Client;

public interface ClientCustomRepository extends BaseCustomRepository<Client> {

    void disableClient(Long id);
    void enableClient(Long id);
    Long findUnfinishedOrderId(Long clientId);
}
