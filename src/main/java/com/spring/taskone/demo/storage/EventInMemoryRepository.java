/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.storage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.spring.taskone.demo.entities.Event;
import com.spring.taskone.demo.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

public class EventInMemoryRepository implements EventRepository {
    private static final Logger log = LoggerFactory.getLogger(EventInMemoryRepository.class);

    private static final String EVENTS = "events";

    @Value("${config.init.data}")
    private String initDataPath;

    private Map<Long, Event> eventInMemoryStorageMap;

    private final ResourceLoader resourceLoader;

    @Autowired
    public EventInMemoryRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    private void setUpData() {
        Resource resource = resourceLoader.getResource(initDataPath);
        try {
            log.debug("Start inmemory Event storage initialization");

            String dataInitJson = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());

            JsonObject objJson = JsonParser.parseString(dataInitJson).getAsJsonObject();
            JsonArray jsonArray = objJson.getAsJsonArray(EVENTS);

            Type eventType = new TypeToken<List<Event>>() {}.getType();
            List<Event> eventList = gson.fromJson(jsonArray, eventType);

            eventInMemoryStorageMap = eventList.stream().collect(Collectors.toMap(Event::getId, event -> event));

            log.debug("Inmemory Event storage initialized successfully from file: {}", initDataPath);
        } catch (IOException exc) {
            log.error("Inmemory Event storage filed to initialized from file: {} with error: {}", initDataPath, exc);

            throw new RuntimeException(exc);
        }
    }

    @Override
    public List<Event> findByTitle(final String title, final int pageSize, final int pageNum) {
        var eventsList = eventInMemoryStorageMap.values().stream().filter(event -> event.getTitle().equalsIgnoreCase(title)).toList();
        return getEvents(pageSize, pageNum, eventsList);
    }

    @Override
    public List<Event> findByDay(final LocalDate day, final int pageSize, final int pageNum) {
        var eventsListByDay = eventInMemoryStorageMap.values().stream().filter(event -> event.getDate().isEqual(day)).collect(Collectors.toList());
        return getEvents(pageSize, pageNum, eventsListByDay);
    }

    @Override
    public Optional<Event> findById(final Long primaryKey) {
        return Optional.ofNullable(eventInMemoryStorageMap.get(primaryKey));
    }

    @Override
    public boolean existsById(final Long primaryKey) {
        return eventInMemoryStorageMap.containsKey(primaryKey);
    }

    @Override
    public Iterable<Event> findAll() {
        return eventInMemoryStorageMap.values().stream().toList();
    }

    @Override
    public long count() {
        return eventInMemoryStorageMap.values().size();
    }

    @Override
    public void delete(final Event entity) {
        eventInMemoryStorageMap.remove(entity.getId());
    }

    @Override
    public <S extends Event> S save(final S entity) {
        if (entity.getId() == null) {
            try {
                Long maxId = eventInMemoryStorageMap.keySet().stream().max(Long::compare).orElseThrow(() -> new IOException("No Event data in memory storage"));
                entity.setId(maxId + 1);
                eventInMemoryStorageMap.put(entity.getId(), entity);

                log.debug("New event saved to repository with ID: {}", entity.getId());

                return entity;

            } catch (IOException exc) {
                log.error("New event saving to repository error with local date: {} and tittle: {}", entity.getDate(), entity.getTitle());
                throw new RuntimeException(exc);
            }
        }
        eventInMemoryStorageMap.put(entity.getId(), entity);

        return entity;
    }

    private List<Event> getEvents(final int pageSize, final int pageNum, final List<Event> eventsList) {
        int eventListSize = eventInMemoryStorageMap.size();
        if (eventListSize / pageSize <= 0) {
            return eventsList;
        }
        if (eventListSize / pageSize < pageNum) {
            log.debug("Page number out of range for current amount of Events data");
            throw new NoSuchElementException("Page number out of range");
        }

        return eventsList.subList((pageNum - 1) * pageSize, pageNum * pageSize - 1);
    }

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
        }
    }).create();
}
