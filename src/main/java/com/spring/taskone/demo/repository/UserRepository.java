/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.repository;

import java.util.List;
import java.util.Optional;

import com.spring.taskone.demo.entities.User;

public interface UserRepository extends StorageRepository<User, Long> {

    Optional<User> getUserByEmail(String email);

    List<User> getUsersByName(String name, int pageSize, int pageNum);
}
