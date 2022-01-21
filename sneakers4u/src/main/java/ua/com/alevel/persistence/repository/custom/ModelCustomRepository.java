package ua.com.alevel.persistence.repository.custom;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.item.attributes.Model;

public interface ModelCustomRepository extends BaseCustomRepository<Model> {

    DataTableResponse<Model> findAllByBrandId(DataTableRequest request, Long brandId);
}
