package ua.com.alevel.service;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.item.attributes.Model;

public interface ModelService extends BaseService<Model> {

    DataTableResponse<Model> findAllByBrandId(DataTableRequest request, Long brandId);
}
