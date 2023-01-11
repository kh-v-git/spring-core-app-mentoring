package com.spring.taskone.demo.service.event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.spring.taskone.demo.entities.Event;
import com.spring.taskone.demo.repository.impl.EventRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EventServiceImpl implements EventService {
    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private EventRepositoryImpl eventRepositoryImpl;

    @Override
    public Optional<Event> getEventById(final long eventId) {
        return eventRepositoryImpl.findById(eventId);
    }

    @Override
    public List<Event> getEventsByTitle(final String title, final int pageSize, final int pageNum) {
        return eventRepositoryImpl.findByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(final LocalDate day, final int pageSize, final int pageNum) {
        return eventRepositoryImpl.findByDay(day, pageSize, pageNum);
    }

    @Override
    public Event createEvent(final Event event) {
        Event savedEvent = eventRepositoryImpl.save(event);

        log.debug("New Event with ID {} was created.", savedEvent.getId());

        return savedEvent;
    }

    @Override
    public Event updateEvent(final Event event) {
        Event savedEvent = eventRepositoryImpl.save(event);

        log.debug("Event with ID {} was updated.", savedEvent.getId());

        return savedEvent;
    }

    @Override
    public boolean deleteEvent(final long eventId) {
        Optional<Event> maybeEvent = eventRepositoryImpl.findById(eventId);
        if (maybeEvent.isEmpty()) {
            log.debug("Event with ID {} was NOT deleted cause NOT found.", eventId);

            return false;
        }
        eventRepositoryImpl.delete(maybeEvent.get());

        log.debug("Event with ID {} was deleted.", eventId);

        return true;
    }
}
