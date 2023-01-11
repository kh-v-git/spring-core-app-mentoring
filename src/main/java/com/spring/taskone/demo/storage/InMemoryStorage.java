package com.spring.taskone.demo.storage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
import com.spring.taskone.demo.entities.AbstractEntity;
import com.spring.taskone.demo.entities.Event;
import com.spring.taskone.demo.entities.Ticket;
import com.spring.taskone.demo.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;

public class InMemoryStorage {
    private static final Logger log = LoggerFactory.getLogger(InMemoryStorage.class);

    private static final String EVENTS = "events";
    private static final String TICKETS = "tickets";
    private static final String USERS = "users";

    @Value("${config.init.data}")
    private String initDataPath;

    private Map<Long, Event> eventInMemoryStorageMap;

    private Map<Long, Ticket> ticketInMemoryStorageMap;

    private Map<Long, User> userInMemoryStorageMap;

    private final ResourceLoader resourceLoader;

    @Autowired
    public InMemoryStorage(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Map<Long, Event> getEventInMemoryStorageMap() {
        return eventInMemoryStorageMap;
    }

    public void setEventInMemoryStorageMap(Map<Long, Event> eventMap) {
        this.eventInMemoryStorageMap = eventMap;
    }

    public Map<Long, Ticket> getTicketInMemoryStorageMap() {
        return ticketInMemoryStorageMap;
    }

    public void setTicketInMemoryStorageMap(Map<Long, Ticket> ticketMap) {
        this.ticketInMemoryStorageMap = ticketMap;
    }

    public Map<Long, User> getUserInMemoryStorageMap() {
        return userInMemoryStorageMap;
    }

    public void setUserInMemoryStorageMap(Map<Long, User> userMap) {
        this.userInMemoryStorageMap = userMap;
    }

    @PostConstruct
    private void setUpData() {
        Resource resource = resourceLoader.getResource(initDataPath);
        try {
            log.debug("Start inmemory storage initialization");

            String dataInitJson = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
            JsonObject objJson = JsonParser.parseString(dataInitJson).getAsJsonObject();

            //Setup InMemory Map
            eventInMemoryStorageMap = getStorageMap(objJson, EVENTS, Event.class);
            log.debug("Inmemory storage for Class: {} initialized successfully.", Event.class);

            ticketInMemoryStorageMap = getStorageMap(objJson, TICKETS, Ticket.class);
            log.debug("Inmemory storage for Class: {} initialized successfully.", Ticket.class);

            userInMemoryStorageMap = getStorageMap(objJson, USERS, User.class);
            log.debug("Inmemory storage for Class: {} initialized successfully.", User.class);

            log.debug("Inmemory storage initialized successfully from file: {}.", initDataPath);
        } catch (IOException exc) {
            log.error("Inmemory storage filed to initialized from file: {} with error: {}.", initDataPath, exc);

            throw new RuntimeException(exc);
        }
    }

    private <T extends AbstractEntity> Map<Long, T> getStorageMap(JsonObject objJson, String name, Class<T> type) {
        JsonArray jsonArray = objJson.getAsJsonArray(name);
        Type entityType = TypeToken.getParameterized(List.class, type).getType();
        List<T> list = gson.fromJson(jsonArray, entityType);

        return list.stream().collect(Collectors.toMap(AbstractEntity::getId, entity -> entity));
    }

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
        }
    }).create();
}
