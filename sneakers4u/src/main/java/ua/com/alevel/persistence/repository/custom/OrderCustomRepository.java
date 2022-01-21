package ua.com.alevel.persistence.repository.custom;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.order.Order;

public interface OrderCustomRepository extends BaseCustomRepository<Order> {

    DataTableResponse<Order> findAllByClientId(DataTableRequest request, Long clientId);
}
