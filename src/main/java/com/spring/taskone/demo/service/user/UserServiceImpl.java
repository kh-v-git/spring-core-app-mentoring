/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.service.user;

import java.util.List;
import java.util.Optional;

import com.spring.taskone.demo.entities.User;
import com.spring.taskone.demo.storage.InMemoryRepositoryStorageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private InMemoryRepositoryStorageImpl inMemoryRepositoryStorage;

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
    public Optional<User> createUser(final User user) {
        return null;
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
