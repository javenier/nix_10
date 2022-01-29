package ua.com.alevel.persistence.repository.custom;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.item.Sneaker;

import java.util.List;

public interface SneakerCustomRepository extends BaseCustomRepository<Sneaker> {

    DataTableResponse<Sneaker> findAllByBrandId(DataTableRequest request, Long brandId);
    DataTableResponse<Sneaker> findAllByModelId(DataTableRequest request, Long modelId);
    DataTableResponse<Sneaker> findAllByOrderId(DataTableRequest request, Long orderId);
    DataTableResponse<Sneaker> findAllByGender(DataTableRequest request, String gender);
    DataTableResponse<Sneaker> findAllBySearchQuery(DataTableRequest request, String query);

    void deleteByBrandId(Long id);
    void deleteByModelId(Long id);

    long countByBrandId(Long brandId);

    List<Object[]> findAllSneakerNames();
}
