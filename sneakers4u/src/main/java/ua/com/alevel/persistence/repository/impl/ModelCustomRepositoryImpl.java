package ua.com.alevel.persistence.repository.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.item.attributes.Model;
import ua.com.alevel.persistence.repository.BrandRepository;
import ua.com.alevel.persistence.repository.custom.ModelCustomRepository;
import ua.com.alevel.persistence.repository.custom.SneakerCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class ModelCustomRepositoryImpl implements ModelCustomRepository {

    private final BrandRepository brandRepository;
    private final SneakerCustomRepository sneakerCustomRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ModelCustomRepositoryImpl(BrandRepository brandRepository, SneakerCustomRepository sneakerCustomRepository) {
        this.brandRepository = brandRepository;
        this.sneakerCustomRepository = sneakerCustomRepository;
    }

    @Override
    public void deleteById(Long id) {
        sneakerCustomRepository.deleteByModelId(id);
        entityManager.
                createQuery("delete from Model m where m.id = :id").
                setParameter("id", id).
                executeUpdate();
    }

    @Override
    public DataTableResponse<Model> findAll(DataTableRequest request) {
        List<Model> models = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Object[]> modelsRl = entityManager.createNativeQuery("select * from models order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize()).getResultList();

        for (Object[] object : modelsRl) {
            Model model = convertResultSetToModel(object);
            models.add(model);
        }

        DataTableResponse<Model> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(models);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }

    private Model convertResultSetToModel(Object[] resultSet) {
        BigInteger id = (BigInteger) resultSet[0];
        Timestamp created = (Timestamp) resultSet[1];
        Timestamp updated = (Timestamp) resultSet[2];
        String name = (String) resultSet[3];
        BigInteger brandId = (BigInteger) resultSet[4];
        Model model = new Model();
        model.setId(id.longValue());
        model.setCreated(new Date(created.getTime()));
        model.setUpdated(new Date(updated.getTime()));
        model.setName(name);
        model.setBrand(brandRepository.getById(brandId.longValue()));
        return model;
    }

    @Override
    public DataTableResponse<Model> findAllByBrandId(DataTableRequest request, Long brandId) {
        List<Model> models = new ArrayList<>();
        Map<Object, Object> otherParamMap = new HashMap<>();
        int limit = (request.getCurrentPage() - 1) * request.getPageSize();

        List<Object[]> modelsRl = entityManager.createNativeQuery("select * from models where brand_id = " + brandId +
                " order by " +
                request.getSort() + " " +
                request.getOrder() + " limit " +
                limit + "," +
                request.getPageSize()).getResultList();

        for (Object[] object : modelsRl) {
            Model model = convertResultSetToModel(object);
            models.add(model);
        }

        DataTableResponse<Model> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(models);
        dataTableResponse.setOtherParam(otherParamMap);
        return dataTableResponse;
    }
}
