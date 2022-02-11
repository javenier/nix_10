package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.user.Client;

public interface ClientService extends BaseService<Client> {

    Client findByEmail(String email);

    void disableClient(Long id);

    void enableClient(Long id);

    Long findUnfinishedOrderId(Long clientId);

    boolean isActivatedByEmail(String code);
}
