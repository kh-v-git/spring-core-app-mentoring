/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2023. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.storage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.spring.taskone.demo.entities.Ticket;
import com.spring.taskone.demo.entities.User;
import com.spring.taskone.demo.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

public class TicketInMemoryRepository implements TicketRepository {
    private static final Logger log = LoggerFactory.getLogger(TicketInMemoryRepository.class);

    private static final String TICKETS = "tickets";

    @Value("${config.init.data}")
    private String initDataPath;

    private Map<Long, Ticket> ticketInMemoryStorageMap;

    private final ResourceLoader resourceLoader;

    @Autowired
    public TicketInMemoryRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    private void setUpData() {
        Resource resource = resourceLoader.getResource(initDataPath);
        try {
            log.debug("Start inmemory Ticket storage initialization");

            String dataInitJson = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());

            JsonObject objJson = JsonParser.parseString(dataInitJson).getAsJsonObject();
            JsonArray jsonArray = objJson.getAsJsonArray(TICKETS);

            Type eventType = new TypeToken<List<Ticket>>() {}.getType();
            List<Ticket> eventList = gson.fromJson(jsonArray, eventType);

            ticketInMemoryStorageMap = eventList.stream().collect(Collectors.toMap(Ticket::getId, ticket -> ticket));

            log.debug("Inmemory Ticket storage initialized successfully from file: {}", initDataPath);
        } catch (IOException exc) {
            log.error("Inmemory Ticket storage filed to initialized from file: {} with error: {}", initDataPath, exc);

            throw new RuntimeException(exc);
        }
    }


    @Override
    public Optional<Ticket> findById(final Long primaryKey) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(final Long primaryKey) {
        return false;
    }

    @Override
    public Iterable<Ticket> findAll() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(final Ticket entity) {

    }

    @Override
    public <S extends Ticket> S save(final S entity) {
        return null;
    }

    @Override
    public List<Ticket> getBookedTickets(final User user, final int pageSize, final int pageNum) {
        return null;
    }

    @Override
    public List<Ticket> getBookedTickets(final Event event, final int pageSize, final int pageNum) {
        return null;
    }

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
        }
    }).create();
}
