/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.storage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.spring.taskone.demo.entities.Event;
import com.spring.taskone.demo.repository.EventRepository;

public class EventInMemoryRepositoryStorageImpl implements EventRepository {
    private final Map<Long, Event> eventStorageMap = new HashMap<>();

    @Override
    public Optional<Event> findById(final long id) {
        return Optional.ofNullable(eventStorageMap.get(id));
    }

    @Override
    public Optional<Event> save(final Event event) {
        return Optional.ofNullable(eventStorageMap.put(event.getId(), event));
    }

    @Override
    public void delete(long id) {
        eventStorageMap.remove(id);
    }

    @Override
    public boolean existsById(final long id) {
        return eventStorageMap.containsKey(id);
    }

    @Override
    public List<Event> findByTitle(final String title, final int pageSize, final int pageNum) {
        return null;
    }

    @Override
    public List<Event> findByDay(final LocalDate day, final int pageSize, final int pageNum) {
        return null;
    }
}
