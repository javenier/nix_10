package ua.com.alevel.service;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.item.Sneaker;
import ua.com.alevel.persistence.entity.item.attributes.Size;

public interface SneakerService extends BaseService<Sneaker> {

    DataTableResponse<Sneaker> findAllByBrandId(DataTableRequest request, Long brandId);
    DataTableResponse<Sneaker> findAllByModelId(DataTableRequest request, Long modelId);
    DataTableResponse<Sneaker> findAllByOrderId(DataTableRequest request, Long orderId);
    DataTableResponse<Sneaker> findAllByGender(DataTableRequest request, String gender);
    DataTableResponse<Sneaker> findAllBySearchQuery(DataTableRequest request, String query);
    void addToCart(Sneaker sneaker, Size size);
}
