/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.service.event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.spring.taskone.demo.entities.Event;
import com.spring.taskone.demo.storage.InMemoryRepositoryStorageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EventServiceImpl implements EventService {
    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private InMemoryRepositoryStorageImpl inMemoryRepositoryStorage;

    @Override
    public Optional<Event> getEventById(final long eventId) {
        return null;
    }

    @Override
    public List<Event> getEventsByTitle(final String title, final int pageSize, final int pageNum) {
        return null;
    }

    @Override
    public List<Event> getEventsForDay(final LocalDate day, final int pageSize, final int pageNum) {
        return null;
    }

    @Override
    public Optional<Event> createEvent(final Event event) {
        return null;
    }

    @Override
    public Optional<Event> updateEvent(final Event event) {
        return null;
    }

    @Override
    public boolean deleteEvent(final long eventId) {
        inMemoryRepositoryStorage.delete(eventId);
        return inMemoryRepositoryStorage.findById(eventId).isEmpty();
    }
}
