package ua.com.alevel.persistence.repository.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.item.attributes.Size;
import ua.com.alevel.persistence.repository.custom.SizeCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class SizeCustomRepositoryImpl implements SizeCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteById(Long id) {
        entityManager.
                createQuery("delete from Size s where s.id = :id").
                setParameter("id", id).
                executeUpdate();
    }

    @Override
    public DataTableResponse<Size> findAll(DataTableRequest request) {
        List<Size> sizes = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Object[]> sizesRl = entityManager.createNativeQuery("select * from sizes order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize()).getResultList();

        for(Object[] object : sizesRl) {
            Size size = convertResultSetToSize(object);
            sizes.add(size);
        }

        DataTableResponse<Size> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(sizes);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    private Size convertResultSetToSize(Object[] resultSet) {
        BigInteger id = (BigInteger) resultSet[0];
        Timestamp created = (Timestamp) resultSet[1];
        Timestamp updated = (Timestamp) resultSet[2];
        Integer sizeValue = (Integer) resultSet[3];
        Size size = new Size();
        size.setId(id.longValue());
        size.setCreated(new Date(created.getTime()));
        size.setUpdated(new Date(updated.getTime()));
        size.setSize(sizeValue);
        return size;
    }

    @Override
    public DataTableResponse<Size> findAllBySneakerId(DataTableRequest request, Long sneakerId) {
        List<Size> sizes = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Object[]> sizesRl = entityManager.createNativeQuery("select s.id, s.created, s.updated," +
                " size from sizes as s left join sneaker_size ss on s.id = ss.size_id where sneaker_id = "
                + sneakerId + " order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize()).getResultList();

        for(Object[] object : sizesRl) {
            Size size = convertResultSetToSize(object);
            sizes.add(size);
        }

        DataTableResponse<Size> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(sizes);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }
}
