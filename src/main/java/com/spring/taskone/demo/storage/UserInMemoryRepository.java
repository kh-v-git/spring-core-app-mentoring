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
import com.spring.taskone.demo.entities.User;
import com.spring.taskone.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

public class UserInMemoryRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(UserInMemoryRepository.class);

    private static final String USERS = "users";

    @Value("${config.init.data}")
    private String initDataPath;

    private Map<Long, User> userInMemoryStorageMap;

    private final ResourceLoader resourceLoader;

    @Autowired
    public UserInMemoryRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    private void setUpData() {
        Resource resource = resourceLoader.getResource(initDataPath);
        try {
            log.debug("Start inmemory Event storage initialization");

            String dataInitJson = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());

            JsonObject objJson = JsonParser.parseString(dataInitJson).getAsJsonObject();
            JsonArray jsonArray = objJson.getAsJsonArray(USERS);

            Type eventType = new TypeToken<List<User>>() {}.getType();
            List<User> eventList = gson.fromJson(jsonArray, eventType);

            userInMemoryStorageMap = eventList.stream().collect(Collectors.toMap(User::getId, user -> user));

            log.debug("Inmemory User storage initialized successfully from file: {}", initDataPath);
        } catch (IOException exc) {
            log.error("Inmemory User storage filed to initialized from file: {} with error: {}", initDataPath, exc);

            throw new RuntimeException(exc);
        }
    }

    @Override
    public List<User> getUsersByName(final String name, final int pageSize, final int pageNum) {
        var userList = userInMemoryStorageMap.values().stream().filter(user -> user.getName().equalsIgnoreCase(name)).toList();
        return getEvents(pageSize, pageNum, userList);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userInMemoryStorageMap.values().stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    @Override
    public Optional<User> findById(final Long primaryKey) {
        return Optional.ofNullable(userInMemoryStorageMap.get(primaryKey));
    }

    @Override
    public boolean existsById(final Long primaryKey) {
        return userInMemoryStorageMap.containsKey(primaryKey);
    }

    @Override
    public Iterable<User> findAll() {
        return userInMemoryStorageMap.values().stream().toList();
    }

    @Override
    public long count() {
        return userInMemoryStorageMap.values().size();
    }

    @Override
    public void delete(final User entity) {
        userInMemoryStorageMap.remove(entity.getId());
    }

    @Override
    public <S extends User> S save(final S entity) {
        if (entity.getId() == null) {
            try {
                Long maxId = userInMemoryStorageMap.keySet().stream().max(Long::compare).orElseThrow(() -> new IOException("No User data in memory storage."));
                entity.setId(maxId + 1);
                userInMemoryStorageMap.put(entity.getId(), entity);

                log.debug("New User saved to repository with ID: {}", entity.getId());

                return entity;

            } catch (IOException exc) {
                log.error("New User saving to repository error with local date: {} and tittle: {}", entity.getName(), entity.getName());
                throw new RuntimeException(exc);
            }
        }
        userInMemoryStorageMap.put(entity.getId(), entity);

        return entity;
    }

    private List<User> getEvents(final int pageSize, final int pageNum, final List<User> userList) {
        int userListSize = userInMemoryStorageMap.size();
        if (userListSize / pageSize <= 0) {
            return userList;
        }
        if (userListSize / pageSize < pageNum) {
            log.debug("Page number out of range for current amount of Users data");
            throw new NoSuchElementException("Page number out of range");
        }

        return userList.subList((pageNum - 1) * pageSize, pageNum * pageSize - 1);
    }

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
        }
    }).create();
}
