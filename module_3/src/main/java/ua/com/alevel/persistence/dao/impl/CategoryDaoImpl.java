package ua.com.alevel.persistence.dao.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.CategoryDao;
import ua.com.alevel.persistence.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryDaoImpl implements CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Category entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(Category entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(Long id) {
        entityManager.createQuery("delete from Category c where c.id = :id").
                setParameter("id", id).
                executeUpdate();
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager.createQuery("select count(c.id) from Category c where c.id = :id").
                setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Category findById(Long id) {
        return entityManager.find(Category.class, id);
    }

    @Override
    public List<Category> findAll() {
        return entityManager.createQuery("select c from Category c", Category.class).getResultList();
    }
}
