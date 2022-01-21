package ua.com.alevel.service;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.item.attributes.Size;

public interface SizeService extends BaseService<Size> {

    void link(Long sneakerId, Long sizeId);
    void unlink(Long sneakerId, Long sizeId);
    DataTableResponse<Size> findAllBySneakerId(DataTableRequest request, Long sneakerId);
}
