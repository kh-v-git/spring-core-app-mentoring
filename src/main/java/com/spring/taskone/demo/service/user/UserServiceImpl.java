/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.service.user;

import java.util.List;
import java.util.Optional;

import com.spring.taskone.demo.entities.Event;
import com.spring.taskone.demo.entities.User;
import com.spring.taskone.demo.storage.UserInMemoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserInMemoryRepository userInMemoryRepository;

    @Override
    public Optional<User> getUserById(final long userId) {
        return null;
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return null;
    }

    @Override
    public List<User> getUsersByName(final String name, final int pageSize, final int pageNum) {
        return null;
    }

    @Override
    public User createUser(final User user) {
        User savedUser = userInMemoryRepository.save(user);

        log.debug("New User with ID {} was created", savedUser.getId());

        return savedUser;
    }

    @Override
    public Optional<User> updateUser(final User user) {
        return null;
    }

    @Override
    public boolean deleteUser(final long userId) {
        return false;
    }
}
