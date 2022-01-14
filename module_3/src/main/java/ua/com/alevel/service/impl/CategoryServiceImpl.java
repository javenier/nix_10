package ua.com.alevel.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.CategoryDao;
import ua.com.alevel.persistence.entity.Category;
import ua.com.alevel.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public void create(Category entity) {
        categoryDao.create(entity);
        LOGGER_INFO.info("Category " + entity.getName() + " has been created");
    }

    @Override
    public void update(Category entity) {
        if(categoryDao.existById(entity.getId())) {
            categoryDao.update(entity);
            LOGGER_INFO.info("Category " + entity.getName() + " has been updated");
        }
        else {
            LOGGER_WARN.warn("Category " + entity.getName() + " has not been updated");
            throw new RuntimeException("not found...");
        }
    }

    @Override
    public void delete(Long id) {
        if(categoryDao.existById(id)) {
            categoryDao.delete(id);
            LOGGER_INFO.info("Category with id = " + id + " has been deleted");
        }
        else {
            LOGGER_WARN.warn("Category with id = " + id + " has not been deleted");
            throw new RuntimeException("not found...");
        }
    }

    @Override
    public Category findById(Long id) {
        Category category = categoryDao.findById(id);
        if(category == null) {
            LOGGER_WARN.warn("Category with id = " + id + " has not been found");
            throw new RuntimeException("not found...");
        }
        LOGGER_INFO.info("Category with id = " + id + " has been found");
        LOGGER_INFO.info("Category with id = " + id + " has been found");
        return category;
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }
}
