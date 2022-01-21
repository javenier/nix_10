package ua.com.alevel.datatable;

import ua.com.alevel.persistence.entity.BaseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DataTableResponse<E extends BaseEntity> {

    private List<E> items;
    private long itemsSize;
    private Map<Object, Object> otherParam;

    public DataTableResponse() {
        items = Collections.emptyList();
        itemsSize = 0;
        otherParam = Collections.emptyMap();
    }

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }

    public long getItemsSize() {
        return itemsSize;
    }

    public void setItemsSize(long itemsSize) {
        this.itemsSize = itemsSize;
    }

    public Map<Object, Object> getOtherParam() {
        return otherParam;
    }

    public void setOtherParam(Map<Object, Object> otherParam) {
        this.otherParam = otherParam;
    }
}