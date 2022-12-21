/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.spring.taskone.demo.entities.Event;

public interface EventRepository {
    Optional<Event> findById(long id);

    Optional<Event> save(Event event);

    void delete(long id);

    boolean existsById(long id);

    List<Event> findByTitle(String title, int pageSize, int pageNum);

    List<Event> findByDay(LocalDate day, int pageSize, int pageNum);
}
