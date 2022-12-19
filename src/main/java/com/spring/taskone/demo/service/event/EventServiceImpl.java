/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.service.event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.spring.taskone.demo.entities.Event;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EventServiceImpl implements EventService {

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
        return false;
    }
}
