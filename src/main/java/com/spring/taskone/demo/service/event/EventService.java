package com.spring.taskone.demo.service.event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.spring.taskone.demo.entities.Event;

public interface EventService {
    Optional<Event> getEventById(long eventId);

    List<Event> getEventsByTitle(String title, int pageSize, int pageNum);

    List<Event> getEventsForDay(LocalDate day, int pageSize, int pageNum);

    Event createEvent(Event event);

    Event updateEvent(Event event);

    boolean deleteEvent(long eventId);
}
