/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.spring.taskone.demo.entities.User;
import com.spring.taskone.demo.repository.UserRepository;

public class UserInMemoryRepositoryStorageImpl implements UserRepository {
    private final Map<Long, User> userStorageMap = new HashMap<>();

    @Override
    public Optional<User> findById(final long id) {
        return Optional.ofNullable(userStorageMap.get(id));
    }

    @Override
    public Optional<User> save(final User user) {
        return Optional.ofNullable(userStorageMap.put(user.getId(), user));
    }

    @Override
    public void delete(final long id) {
        userStorageMap.remove(id);
    }

    @Override
    public boolean existsById(final long id) {
        return userStorageMap.containsKey(id);
    }
}
