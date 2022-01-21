package ua.com.alevel.persistence.repository.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.item.attributes.Brand;
import ua.com.alevel.persistence.repository.custom.BrandCustomRepository;
import ua.com.alevel.persistence.repository.custom.SneakerCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class BrandCustomRepositoryImpl implements BrandCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final SneakerCustomRepository sneakerCustomRepository;

    public BrandCustomRepositoryImpl(SneakerCustomRepository sneakerCustomRepository) {
        this.sneakerCustomRepository = sneakerCustomRepository;
    }

    @Override
    public DataTableResponse<Brand> findAll(DataTableRequest request) {
        List<Brand> brands = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Object[]> brandsRl = entityManager.createNativeQuery("select * from brands order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize()).getResultList();

        for (Object[] object : brandsRl) {
            Brand brand = convertResultSetToBrand(object);
            brands.add(brand);
        }

        DataTableResponse<Brand> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(brands);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    @Override
    public void deleteById(Long id) {
        sneakerCustomRepository.deleteByBrandId(id);
//        List<Long> modelIds =  entityManager.
//                createQuery("select m.id from Model m where m.brand.id = :id").
//                setParameter("id", id).
//                getResultList();
//
//        if (CollectionUtils.isNotEmpty(modelIds)) {
//            List<Long> sneakerIds = entityManager.
//                    createQuery("select s.id from Sneaker s where s.model.id in :modelIds").
//                    setParameter("modelIds", id).
//                    getResultList();
//            if (CollectionUtils.isNotEmpty(sneakerIds)) {
//                entityManager.
//                        createQuery("delete from Sneaker s where s.id in :sneakerIds").
//                        setParameter("sneakerIds", id).
//                        executeUpdate();
//            }
//        }

        entityManager.
                createQuery("delete from Brand b where b.id = :id").
                setParameter("id", id).
                executeUpdate();
    }

    private Brand convertResultSetToBrand(Object[] resultSet) {
        BigInteger id = (BigInteger) resultSet[0];
        Timestamp created = (Timestamp) resultSet[1];
        Timestamp updated = (Timestamp) resultSet[2];
        String imageUrl = (String) resultSet[3];
        String name = (String) resultSet[4];
        Brand brand = new Brand();
        brand.setId(id.longValue());
        brand.setCreated(new Date(created.getTime()));
        brand.setUpdated(new Date(updated.getTime()));
        brand.setImageUrl(imageUrl);
        brand.setName(name);
        return brand;
    }
}
