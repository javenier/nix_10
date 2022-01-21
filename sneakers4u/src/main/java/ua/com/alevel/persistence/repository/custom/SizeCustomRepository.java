package ua.com.alevel.persistence.repository.custom;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.item.attributes.Size;

public interface SizeCustomRepository extends BaseCustomRepository<Size> {

    DataTableResponse<Size> findAllBySneakerId(DataTableRequest request, Long sneakerId);
}
