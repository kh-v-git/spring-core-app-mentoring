package com.spring.taskone.demo.repository.impl;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.spring.taskone.demo.entities.User;
import com.spring.taskone.demo.repository.UserRepository;
import com.spring.taskone.demo.storage.InMemoryStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private final InMemoryStorage inMemoryStorage;

    @Autowired
    public UserRepositoryImpl(InMemoryStorage inMemoryStorage) {
        this.inMemoryStorage = inMemoryStorage;
    }

    @Override
    public List<User> getUsersByName(final String name, final int pageSize, final int pageNum) {
        var userList = inMemoryStorage.getUserInMemoryStorageMap().values().stream().filter(user -> user.getName().equalsIgnoreCase(name)).toList();
        return getUsers(pageSize, pageNum, userList);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return inMemoryStorage.getUserInMemoryStorageMap().values().stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    @Override
    public Optional<User> findById(final Long primaryKey) {
        return Optional.ofNullable(inMemoryStorage.getUserInMemoryStorageMap().get(primaryKey));
    }

    @Override
    public boolean existsById(final Long primaryKey) {
        return inMemoryStorage.getUserInMemoryStorageMap().containsKey(primaryKey);
    }

    @Override
    public Iterable<User> findAll() {
        return inMemoryStorage.getUserInMemoryStorageMap().values().stream().toList();
    }

    @Override
    public long count() {
        return inMemoryStorage.getUserInMemoryStorageMap().values().size();
    }

    @Override
    public void delete(final User entity) {
        inMemoryStorage.getUserInMemoryStorageMap().remove(entity.getId());
    }

    @Override
    public <S extends User> S save(final S entity) {
        if (entity.getId() == null) {

            Long maxId = inMemoryStorage.getUserInMemoryStorageMap().keySet().stream().max(Long::compare).orElse(0L);
            entity.setId(maxId + 1);
            inMemoryStorage.getUserInMemoryStorageMap().put(entity.getId(), entity);

            log.debug("New User saved to repository with ID: {}.", entity.getId());

            return entity;
        }

        inMemoryStorage.getUserInMemoryStorageMap().put(entity.getId(), entity);
        log.debug("User updated in repository with ID: {}.", entity.getId());

        return entity;
    }

    private List<User> getUsers(final int pageSize, final int pageNum, final List<User> userList) {
        int userListSize = userList.size();
        if (userListSize / pageSize <= 1) {
            return userList;
        }
        if (userListSize / pageSize < pageNum) {
            return Lists.newArrayList();
        }

        return userList.subList((pageNum - 1) * pageSize, pageNum * pageSize - 1);
    }
}
