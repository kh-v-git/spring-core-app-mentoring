package com.spring.taskone.demo.service.user;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.spring.taskone.demo.entities.User;
import com.spring.taskone.demo.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final long USER_ID = 20L;
    private static final int PAGE_SIZE = 16;
    private static final int PAGE_NUMBER = 32;
    private static final String USER_EMAIL = "jhon_macklein@die.hard";
    private static final String USER_NAME = "John Macklein";

    @Mock
    private User user;

    @Mock
    private UserRepositoryImpl userRepository;

    @InjectMocks
    private UserServiceImpl testingInstance;

    @Test
    void shouldGetUserById() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(user.getId()).thenReturn(USER_ID);

        Optional<User> result = testingInstance.getUserById(USER_ID);

        assertThat(result.isPresent(), is(Boolean.TRUE));
        assertThat(result.get().getId(), is(USER_ID));
    }

    @Test
    void shouldGetUserByEmail() {
        when(userRepository.getUserByEmail(USER_EMAIL)).thenReturn(Optional.of(user));
        when(user.getEmail()).thenReturn(USER_EMAIL);

        Optional<User> result = testingInstance.getUserByEmail(USER_EMAIL);

        assertThat(result.isPresent(), is(Boolean.TRUE));
        assertThat(result.get().getEmail(), is(USER_EMAIL));
    }

    @Test
    void shouldGetUsersByName() {
        List<User> eventList = Lists.newArrayList(user);
        when(user.getName()).thenReturn(USER_NAME);
        when(userRepository.getUsersByName(USER_NAME, PAGE_SIZE, PAGE_NUMBER)).thenReturn(eventList);

        List<User> result = testingInstance.getUsersByName(USER_NAME, PAGE_SIZE, PAGE_NUMBER);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is(USER_NAME));
    }

    @Test
    void shouldCreateUser() {
        when(userRepository.save(user)).thenReturn(user);
        when(user.getName()).thenReturn(USER_NAME);
        when(user.getEmail()).thenReturn(USER_EMAIL);

        User result = testingInstance.createUser(user);

        assertThat(result, is(notNullValue()));
        assertThat(result.getName(), is(USER_NAME));
        assertThat(result.getEmail(), is(USER_EMAIL));
    }

    @Test
    void shouldUpdateUser() {
        when(userRepository.save(user)).thenReturn(user);
        when(user.getId()).thenReturn(USER_ID);
        when(user.getName()).thenReturn(USER_NAME);
        when(user.getEmail()).thenReturn(USER_EMAIL);

        User result = testingInstance.updateUser(user);

        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(USER_ID));
        assertThat(result.getName(), is(USER_NAME));
        assertThat(result.getEmail(), is(USER_EMAIL));
    }

    @Test
    void shouldDeleteUser() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        boolean result = testingInstance.deleteUser(USER_ID);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(Boolean.TRUE));
    }

    @Test
    void shouldNotDeleteUser() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        boolean result = testingInstance.deleteUser(USER_ID);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(Boolean.FALSE));
    }
}
