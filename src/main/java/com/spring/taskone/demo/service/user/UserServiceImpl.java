package com.spring.taskone.demo.service.user;

import java.util.List;
import java.util.Optional;

import com.spring.taskone.demo.entities.User;
import com.spring.taskone.demo.repository.impl.UserRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Override
    public Optional<User> getUserById(final long userId) {
        return userRepositoryImpl.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepositoryImpl.getUserByEmail(email);
    }

    @Override
    public List<User> getUsersByName(final String name, final int pageSize, final int pageNum) {
        return userRepositoryImpl.getUsersByName(name, pageSize, pageNum);
    }

    @Override
    public User createUser(final User user) {
        User savedUser = userRepositoryImpl.save(user);

        log.debug("New User with ID {} was created.", savedUser.getId());

        return savedUser;
    }

    @Override
    public User updateUser(final User user) {
        User savedUser = userRepositoryImpl.save(user);

        log.debug("User with ID {} was updated.", savedUser.getId());
        return savedUser;
    }

    @Override
    public boolean deleteUser(final long userId) {
        Optional<User> maybeUser = userRepositoryImpl.findById(userId);
        if (maybeUser.isEmpty()) {
            log.debug("User with ID {} was NOT deleted cause NOT found.", userId);

            return false;
        }
        userRepositoryImpl.delete(maybeUser.get());

        log.debug("User with ID {} was deleted.", userId);

        return true;
    }
}
