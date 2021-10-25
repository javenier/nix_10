package ua.com.alevel.service;

import ua.com.alevel.dao.UserDao;
import ua.com.alevel.entity.User;

public class UserService {

    private final UserDao userDao = new UserDao();

    public void create(User user) {
        if (!userDao.existByEmail(user.getEmail())) {
            userDao.create(user);
        } else {
            System.out.println("User exists by email");
        }
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(String id) {
        userDao.delete(id);
    }

    public User findById(String id) {
        return userDao.findById(id);
    }

    public User[] findAll() {
        return userDao.findAll();
    }
}
