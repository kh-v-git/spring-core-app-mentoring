/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.service.user;

import java.util.List;
import java.util.Optional;

import com.spring.taskone.demo.entities.User;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserServiceImpl implements UserService {

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
