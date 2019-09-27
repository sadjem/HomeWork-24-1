package com.lepet.dao;

import com.lepet.model.User;

import java.util.LinkedList;
import java.util.List;

public class UserDao {
    private List<User> users = new LinkedList<>();
    private static UserDao instance;

    public static synchronized UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    private UserDao() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
