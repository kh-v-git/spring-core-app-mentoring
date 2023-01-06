/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.service.event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.spring.taskone.demo.entities.Event;
import com.spring.taskone.demo.storage.EventInMemoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EventServiceImpl implements EventService {
    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private EventInMemoryRepository eventInMemoryRepository;

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
    public Event createEvent(final Event event) {
        Event savedEvent = eventInMemoryRepository.save(event);

        log.debug("New Event with ID {} was created", savedEvent.getId());

        return savedEvent;
    }

    @Override
    public Optional<Event> updateEvent(final Event event) {
        return null;
    }

    @Override
    public boolean deleteEvent(final long eventId) {

        return false;
    }
}
