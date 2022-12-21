/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.repository;

import java.util.Optional;

import com.spring.taskone.demo.entities.User;

public interface UserRepository {
    Optional<User> findById(long id);

    Optional<User> save(User user);

    void delete(long id);

    boolean existsById(long id);
}
