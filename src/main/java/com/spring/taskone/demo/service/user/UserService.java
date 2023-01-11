package com.spring.taskone.demo.service.user;

import java.util.List;
import java.util.Optional;

import com.spring.taskone.demo.entities.User;

public interface UserService {
    Optional<User> getUserById(long userId);

    Optional<User> getUserByEmail(String email);

    List<User> getUsersByName(String name, int pageSize, int pageNum);

    User createUser(User user);

    User updateUser(User user);

    boolean deleteUser(long userId);
}
