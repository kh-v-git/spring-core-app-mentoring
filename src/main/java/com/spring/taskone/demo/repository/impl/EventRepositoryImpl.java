package com.spring.taskone.demo.repository.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.spring.taskone.demo.entities.Event;
import com.spring.taskone.demo.repository.EventRepository;
import com.spring.taskone.demo.storage.InMemoryStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EventRepositoryImpl implements EventRepository {
    private static final Logger log = LoggerFactory.getLogger(EventRepositoryImpl.class);

    private final InMemoryStorage inMemoryStorage;

    @Autowired
    public EventRepositoryImpl(InMemoryStorage inMemoryStorage) {
        this.inMemoryStorage = inMemoryStorage;
    }

    @Override
    public List<Event> findByTitle(final String title, final int pageSize, final int pageNum) {
        var eventsList = inMemoryStorage.getEventInMemoryStorageMap().values().stream().filter(event -> event.getTitle().equalsIgnoreCase(title)).toList();
        return getEvents(pageSize, pageNum, eventsList);
    }

    @Override
    public List<Event> findByDay(final LocalDate day, final int pageSize, final int pageNum) {
        var eventsListByDay = inMemoryStorage.getEventInMemoryStorageMap().values().stream().filter(event -> event.getDate().isEqual(day)).collect(Collectors.toList());
        return getEvents(pageSize, pageNum, eventsListByDay);
    }

    @Override
    public Optional<Event> findById(final Long primaryKey) {
        return Optional.ofNullable(inMemoryStorage.getEventInMemoryStorageMap().get(primaryKey));
    }

    @Override
    public boolean existsById(final Long primaryKey) {
        return inMemoryStorage.getEventInMemoryStorageMap().containsKey(primaryKey);
    }

    @Override
    public Iterable<Event> findAll() {
        return inMemoryStorage.getEventInMemoryStorageMap().values().stream().toList();
    }

    @Override
    public long count() {
        return inMemoryStorage.getEventInMemoryStorageMap().values().size();
    }

    @Override
    public void delete(final Event entity) {
        inMemoryStorage.getEventInMemoryStorageMap().remove(entity.getId());
    }

    @Override
    public <S extends Event> S save(final S entity) {
        var inMemEventMap = inMemoryStorage.getEventInMemoryStorageMap();
        if (entity.getId() == null) {

            Long maxId = inMemEventMap.keySet().stream().max(Long::compare).orElse(0L);
            entity.setId(maxId + 1);
            inMemEventMap.put(entity.getId(), entity);
            inMemoryStorage.setEventInMemoryStorageMap(inMemEventMap);
            log.debug("New Event saved to repository with ID: {}.", entity.getId());

            return entity;
        }

        inMemEventMap.put(entity.getId(), entity);
        inMemoryStorage.setEventInMemoryStorageMap(inMemEventMap);
        log.debug("Event updated in repository with ID: {}.", entity.getId());

        return entity;
    }

    private List<Event> getEvents(final int pageSize, final int pageNum, final List<Event> eventsList) {
        int eventListSize = eventsList.size();
        if (eventListSize / pageSize <= 1) {
            return eventsList;
        }
        if (eventListSize / pageSize < pageNum) {
            return Lists.newArrayList();
        }

        return eventsList.subList((pageNum - 1) * pageSize, pageNum * pageSize - 1);
    }
}
