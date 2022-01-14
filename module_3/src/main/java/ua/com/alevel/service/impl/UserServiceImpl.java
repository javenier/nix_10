package ua.com.alevel.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.UserDao;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void create(User entity) {
        userDao.create(entity);
        LOGGER_INFO.info("User " + entity.getFirstName() + " " + entity.getLastName() + " has been created");
    }

    @Override
    public void update(User entity) {
        if(userDao.existById(entity.getId())) {
            userDao.update(entity);
            LOGGER_INFO.info("User " + entity.getFirstName() + " " + entity.getLastName() + " has been updated");
        }
        else {
            LOGGER_WARN.warn("User " + entity.getFirstName() + " " + entity.getLastName() + " has not been found");
            throw new RuntimeException("not found...");
        }
    }

    @Override
    public void delete(Long id) {
        if(userDao.existById(id)) {
            userDao.delete(id);
            LOGGER_INFO.info("User with id = " + id + " has been deleted");
        }
        else {
            LOGGER_WARN.warn("User with id = " + id + " has not been found");
            throw new RuntimeException("not found...");
        }
    }

    @Override
    public User findById(Long id) {
        User user = userDao.findById(id);
        if(user == null) {
            LOGGER_WARN.warn("User with id = " + id + " has not been found");
            throw new RuntimeException("not found...");
        }
        LOGGER_INFO.info("User with id = " + id + " has been found");
        return user;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
