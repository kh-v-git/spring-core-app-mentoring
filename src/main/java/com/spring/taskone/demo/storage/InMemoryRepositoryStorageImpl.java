/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.storage;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.spring.taskone.demo.entities.Event;
import com.spring.taskone.demo.entities.Ticket;
import com.spring.taskone.demo.entities.User;
import com.spring.taskone.demo.repository.EventRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

public class InMemoryRepositoryStorageImpl implements EventRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryRepositoryStorageImpl.class);

    private static final String EVENTS = "events";

    @Value("${config.init.data}")
    private String initDataPath;

    private Map<String, Set<?>> inMemoryStorageMap;

    private final ResourceLoader resourceLoader;

    @Autowired
    public InMemoryRepositoryStorageImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    private void setUpData() {
        Resource resource = resourceLoader.getResource(initDataPath);
        try {
            log.debug("Start inmemory storage initialization");

            inMemoryStorageMap = new HashMap<>();

            String context = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
            JSONObject jsonObject = new JSONObject(context);

            JSONArray jsonArrayEvents = jsonObject.getJSONArray(EVENTS);
            var eventList = (List<Event>) (List<?>) jsonArrayEvents.toList();
            Set<Event> eventHashSet = new HashSet<>(eventList);
            inMemoryStorageMap.put(EVENTS, eventHashSet);

            JSONArray jsonArrayTickets = jsonObject.getJSONArray("tickets");
            var ticketList = (List<Ticket>) (List<?>) jsonArrayTickets.toList();
            Set<Ticket> ticketSet = new HashSet<>(ticketList);
            inMemoryStorageMap.put("tickets", ticketSet);

            JSONArray jsonArrayUsers = jsonObject.getJSONArray("users");
            var usersList = (List<User>) (List<?>) jsonArrayUsers.toList();
            Set<User> userSet = new HashSet<>(usersList);
            inMemoryStorageMap.put("users", userSet);

            log.debug("Inmemory storage initialized successfully from file: {}", initDataPath);
        } catch (IOException exc) {
            log.error("Inmemory storage filed to initialized from file: {} with error: {}", initDataPath, exc);

            throw new RuntimeException(exc);
        }
    }

    @Override
    public Optional<Event> findById(final long id) {
        var eventSet = (Set<Event>) inMemoryStorageMap.get(EVENTS);

        return eventSet.stream().filter(event -> event.getId() == id).findFirst();
    }

    @Override
    public Optional<Event> save(final Event event) {
        var eventSet = (Set<Event>) inMemoryStorageMap.get(EVENTS);
        boolean exists = eventSet.add(event);
        inMemoryStorageMap.put(EVENTS, eventSet);
        if (exists) {

            return Optional.of(event);
        }

        return Optional.empty();
    }

    @Override
    public void delete(long id) {
        var eventSet = (Set<Event>) inMemoryStorageMap.get(EVENTS);
        Event eventToDelete = eventSet.stream().filter(event -> event.getId() == id).findFirst().orElse(null);
        eventSet.remove(eventToDelete);
        inMemoryStorageMap.put(EVENTS, eventSet);
    }

    @Override
    public boolean existsById(final long id) {
        var eventSet = (Set<Event>) inMemoryStorageMap.get(EVENTS);

        return eventSet.stream().anyMatch(event -> event.getId() == id);
    }

    @Override
    public List<Event> findByTitle(final String title, final int pageSize, final int pageNum) {
        var eventSet = (Set<Event>) inMemoryStorageMap.get(EVENTS);
        var sortedByTitleEvents = eventSet.stream().filter(event -> event.getTitle().equalsIgnoreCase(title)).toList();

        int listSize = sortedByTitleEvents.size();

        if (listSize <= pageSize * pageNum || listSize == 0) {
            return sortedByTitleEvents;
        }
        //todo
        return null;
    }

    @Override
    public List<Event> findByDay(final LocalDate day, final int pageSize, final int pageNum) {
        return null;
    }
}
