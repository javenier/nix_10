package ua.com.alevel.db;

import ua.com.alevel.entity.User;
import java.util.Arrays;
import java.util.UUID;

public class DBUser {

    private User[] users;
    private static DBUser instance;
    private static int usersCount;

    private DBUser() {
        users = new User[0];
        usersCount = 0;
    }

    public int getCount() {
        return this.usersCount;
    }

    public static DBUser getInstance() {
        if (instance == null)
            instance = new DBUser();
        return instance;
    }

    public void create(User user) {
        user.setId(generateId());
        if (users.length == usersCount)
            users = Arrays.copyOf(users, usersCount + 1);
        users[usersCount++] = user;
    }

    public void update(User user) {
        User current = findById(user.getId());
        if (current == null)
            return;
        current.setAge(user.getAge());
        current.setName(user.getName());
    }

    public void delete(String id) {
        int index = -1;
        for (int i = 0; i < users.length; i++) {
            if (users[i].getId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index < 0 || users == null || index >= users.length) {
            System.out.println("User not found");
            return;
        }
        User[] temp = new User[users.length - 1];
        for (int i = 0, k = 0; i < users.length; i++) {
            if (i == index) {
                continue;
            }
            temp[k++] = users[i];
        }
        users = Arrays.copyOf(temp, temp.length);
    }

    public User findById(String id) {
        for (int i = 0; i < users.length; i++) {
            if (users[i].getId().equals(id)) {
                return users[i];
            }
        }
        System.out.println("User not found");
        return null;
    }

    public User[] findAll() {
        return users;
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        for (int i = 0; i < users.length; i++) {
            if (users[i].getId().equals(id))
                return generateId();
        }
        return id;
    }

    public boolean existByEmail(String email) {
        for (int i = 0; i < users.length; i++) {
            if (users[i].getEmail().equals(email))
                return true;
        }
        return false;
    }
}
