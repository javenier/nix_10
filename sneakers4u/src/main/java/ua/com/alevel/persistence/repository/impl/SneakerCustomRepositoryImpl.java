package ua.com.alevel.persistence.repository.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.item.Sneaker;
import ua.com.alevel.persistence.repository.BrandRepository;
import ua.com.alevel.persistence.repository.ModelRepository;
import ua.com.alevel.persistence.repository.custom.SneakerCustomRepository;
import ua.com.alevel.persistence.type.Gender;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Service
public class SneakerCustomRepositoryImpl implements SneakerCustomRepository {

    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public SneakerCustomRepositoryImpl(BrandRepository brandRepository, ModelRepository modelRepository) {
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        entityManager.
                createQuery("delete from Sneaker s where s.id = :id").
                setParameter("id", id).
                executeUpdate();
    }

    @Override
    @Transactional
    public DataTableResponse<Sneaker> findAll(DataTableRequest request) {
        List<Sneaker> sneakers = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Object[]> sneakersRl = entityManager.createNativeQuery("select * from sneakers order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize()).getResultList();

        for (Object[] object : sneakersRl) {
            Sneaker sneaker = convertResultSetToSneaker(object);
            sneakers.add(sneaker);
        }

        DataTableResponse<Sneaker> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(sneakers);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    private Sneaker convertResultSetToSneaker(Object[] resultSet) {
        BigInteger id = (BigInteger) resultSet[0];
        Timestamp created = (Timestamp) resultSet[1];
        Timestamp updated = (Timestamp) resultSet[2];
        String description = (String) resultSet[3];
        String imageUrl = (String) resultSet[4];
        BigInteger price = (BigInteger) resultSet[5];
        Integer quantity = (Integer) resultSet[6];
        String sneakerGender = (String) resultSet[7];
        String versionOfModel = (String) resultSet[8];
        BigInteger modelId = (BigInteger) resultSet[9];
        Sneaker sneaker = new Sneaker();
        sneaker.setId(id.longValue());
        sneaker.setCreated(new Date(created.getTime()));
        sneaker.setUpdated(new Date(updated.getTime()));
        sneaker.setDescription(description);
        sneaker.setImageUrl(imageUrl);
        sneaker.setPrice(price.longValue());
        sneaker.setQuantity(quantity.intValue());
        sneaker.setSneakerGender(sneakerGender.equals("MALE") ? Gender.MALE : Gender.FEMALE);
        sneaker.setVersionOfModel(versionOfModel);
        sneaker.setModel(modelRepository.findById(modelId.longValue()).get());
        return sneaker;
    }

    @Override
    @Transactional
    public DataTableResponse<Sneaker> findAllByBrandId(DataTableRequest request, Long brandId) {
        List<Sneaker> sneakers = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Object[]> sneakersRl = entityManager.createNativeQuery("select s.id, s.created, s.updated, description," +
                " image_url, price, quantity, sneaker_gender, version_of_model, model_id from sneakers as s inner join" +
                " models as m on s.model_id = m.id where m.brand_id = " + brandId + " order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize()).getResultList();

        for (Object[] object : sneakersRl) {
            Sneaker sneaker = convertResultSetToSneaker(object);
            sneakers.add(sneaker);
        }

        DataTableResponse<Sneaker> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(sneakers);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    @Override
    @Transactional
    public DataTableResponse<Sneaker> findAllByModelId(DataTableRequest request, Long modelId) {
        List<Sneaker> sneakers = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Object[]> sneakersRl = entityManager.createNativeQuery("select * from sneakers where model_id" +
                " = " + modelId + " order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize()).getResultList();

        for (Object[] object : sneakersRl) {
            Sneaker sneaker = convertResultSetToSneaker(object);
            sneakers.add(sneaker);
        }

        DataTableResponse<Sneaker> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(sneakers);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    @Override
    @Transactional
    public DataTableResponse<Sneaker> findAllByOrderId(DataTableRequest request, Long orderId) {
        List<Sneaker> sneakers = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Object[]> sneakersRl = entityManager.createNativeQuery("select s.id, s.created, s.updated," +
                " description, image_url, price, quantity, sneaker_gender, version_of_model, model_id from sneakers" +
                " as s left join order_sneaker as os on s.id = os.sneaker_id where order_id" +
                " = " + orderId + " order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize()).getResultList();

        for (Object[] object : sneakersRl) {
            Sneaker sneaker = convertResultSetToSneaker(object);
            sneakers.add(sneaker);
        }

        DataTableResponse<Sneaker> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(sneakers);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    @Override
    @Transactional
    public DataTableResponse<Sneaker> findAllByGender(DataTableRequest request, String gender) {
        List<Sneaker> sneakers = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Object[]> sneakersRl = entityManager.createNativeQuery("select * from sneakers where sneaker_gender = " +
                gender + " order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize()).getResultList();

        for (Object[] object : sneakersRl) {
            Sneaker sneaker = convertResultSetToSneaker(object);
            sneakers.add(sneaker);
        }

        DataTableResponse<Sneaker> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(sneakers);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    @Override
    @Transactional
    public void deleteByBrandId(Long id) {
        List<Long> modelIds = entityManager.
                createQuery("select m.id from Model m where m.brand.id = :id").
                setParameter("id", id).
                getResultList();
        delete(modelIds, null);
    }

    @Override
    @Transactional
    public void deleteByModelId(Long id) {
        List<Long> sneakerIds = entityManager.
                createQuery("select s.id from Sneaker s where s.model.id = :id").
                setParameter("id", id).
                getResultList();
        delete(null, sneakerIds);
    }

    private void delete(List<Long> modelIds, List<Long> sneakerIds) {
        if(sneakerIds == null) {
            if (CollectionUtils.isNotEmpty(modelIds)) {
                sneakerIds = entityManager.
                        createQuery("select s.id from Sneaker s where s.model.id in :modelIds").
                        setParameter("modelIds", modelIds).
                        getResultList();
                if (CollectionUtils.isNotEmpty(sneakerIds)) {
                    entityManager.
                            createQuery("delete from Sneaker s where s.id in :sneakerIds").
                            setParameter("sneakerIds", sneakerIds).
                            executeUpdate();
                }
            }
        } else {
            entityManager.
                    createQuery("delete from Sneaker s where s.id in :sneakerIds").
                    setParameter("sneakerIds", sneakerIds).
                    executeUpdate();
        }
    }
}
