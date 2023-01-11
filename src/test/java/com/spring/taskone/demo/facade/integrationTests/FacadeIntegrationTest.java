package com.spring.taskone.demo.facade.integrationTests;

import java.time.LocalDate;
import java.util.List;

import com.spring.taskone.demo.entities.Event;
import com.spring.taskone.demo.entities.Ticket;
import com.spring.taskone.demo.entities.User;
import com.spring.taskone.demo.facade.BookingFacade;
import com.spring.taskone.demo.storage.InMemoryStorage;
import com.spring.taskone.demo.utils.TicketCategoryEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@SpringBootTest
public class FacadeIntegrationTest {
    private static final long EVENT_ID = 10L;
    private static final long USER_ID = 20L;
    private static final long TICKET_ID = 30L;
    private static final int PAGE_SIZE = 16;
    private static final int PAGE_NUMBER = 32;
    private static final int PLACE_NUMBER = 128;
    private static final String EVENT_TITLE = "Test Title";
    private static final String EVENT_TITLE_1 = "Tset Eltit";
    private static final String USER_EMAIL = "john_macklein@die.hard";
    private static final String USER_EMAIL_1 = "nhoj_macklein@die.hard";
    private static final String USER_NAME = "John Macklein";
    private static final String USER_NAME_1 = "Nhoj Macklein";
    private static final LocalDate EVENT_DATE = LocalDate.of(2023, 10, 12);
    private static final LocalDate EVENT_DATE_1 = LocalDate.of(2024, 11, 9);
    private static final TicketCategoryEnum TICKET_CATEGORY = TicketCategoryEnum.PREMIUM;
    @Autowired
    BookingFacade testInstance;

    @Autowired
    InMemoryStorage inMemoryStorage;

    @Test
    public void shouldCreateEvent() {
        Event event = new Event(EVENT_DATE, EVENT_TITLE);

        Event result = testInstance.createEvent(event);

        assertThat(result.getId(), is(notNullValue()));
        assertThat(result.getDate(), is(EVENT_DATE));
        assertThat(result.getTitle(), is(EVENT_TITLE));
        Event storedEvent = inMemoryStorage.getEventInMemoryStorageMap().get(result.getId());
        assertThat(result, is(storedEvent));
    }

    @Test
    public void shouldGetEventById() {
        Event event = new Event(EVENT_ID, EVENT_DATE, EVENT_TITLE);
        inMemoryStorage.getEventInMemoryStorageMap().put(EVENT_ID, event);

        Event result = testInstance.getEventById(EVENT_ID);

        assertThat(result.getDate(), is(EVENT_DATE));
        assertThat(result.getTitle(), is(EVENT_TITLE));
        assertThat(result, is(event));
    }

    @Test
    public void shouldUpdateEvent() {
        Event event = new Event(EVENT_ID, EVENT_DATE, EVENT_TITLE);
        inMemoryStorage.getEventInMemoryStorageMap().put(EVENT_ID, event);
        Event updateEvent = new Event(EVENT_ID, EVENT_DATE_1, EVENT_TITLE_1);

        Event result = testInstance.updateEvent(updateEvent);

        assertThat(result.getDate(), is(EVENT_DATE_1));
        assertThat(result.getTitle(), is(EVENT_TITLE_1));
        assertThat(result, is(updateEvent));
        Event storedEvent = inMemoryStorage.getEventInMemoryStorageMap().get(EVENT_ID);
        assertThat(result, is(storedEvent));
    }

    @Test
    public void shouldDeleteEventById() {
        Event event = new Event(EVENT_ID, EVENT_DATE, EVENT_TITLE);
        inMemoryStorage.getEventInMemoryStorageMap().put(EVENT_ID, event);

        boolean result = testInstance.deleteEvent(EVENT_ID);

        assertThat(result, is(Boolean.TRUE));
        Event storedEvent = inMemoryStorage.getEventInMemoryStorageMap().get(EVENT_ID);
        assertThat(storedEvent, is(nullValue()));
    }

    @Test
    public void shouldCreateUser() {
        User user = new User(USER_NAME, USER_EMAIL);

        User result = testInstance.createUser(user);

        assertThat(result.getId(), is(notNullValue()));
        assertThat(result.getName(), is(USER_NAME));
        assertThat(result.getEmail(), is(USER_EMAIL));
        User storedUser = inMemoryStorage.getUserInMemoryStorageMap().get(result.getId());
        assertThat(result, is(storedUser));
    }

    @Test
    public void shouldGetUser() {
        User user = new User(USER_ID, USER_NAME, USER_EMAIL);
        inMemoryStorage.getUserInMemoryStorageMap().put(USER_ID, user);

        User result = testInstance.getUserById(USER_ID);

        assertThat(result.getId(), is(USER_ID));
        assertThat(result.getName(), is(USER_NAME));
        assertThat(result.getEmail(), is(USER_EMAIL));
        assertThat(result, is(user));
    }

    @Test
    public void shouldUpdateUser() {
        User user = new User(USER_ID, USER_NAME, USER_EMAIL);
        inMemoryStorage.getUserInMemoryStorageMap().put(USER_ID, user);
        User updateUser = new User(USER_ID, USER_NAME_1, USER_EMAIL_1);

        User result = testInstance.updateUser(updateUser);

        assertThat(result.getName(), is(USER_NAME_1));
        assertThat(result.getEmail(), is(USER_EMAIL_1));
        assertThat(result, is(updateUser));
        User storedUser = inMemoryStorage.getUserInMemoryStorageMap().get(USER_ID);
        assertThat(result, is(storedUser));
    }

    @Test
    public void shouldDeleteUser() {
        User user = new User(USER_ID, USER_NAME, USER_EMAIL);
        inMemoryStorage.getUserInMemoryStorageMap().put(USER_ID, user);

        boolean result = testInstance.deleteUser(USER_ID);

        assertThat(result, is(Boolean.TRUE));
        User storedUser = inMemoryStorage.getUserInMemoryStorageMap().get(USER_ID);
        assertThat(storedUser, is(nullValue()));
    }

    @Test
    public void shouldBookTicket() {

        Ticket result = testInstance.bookTicket(USER_ID, EVENT_ID, PLACE_NUMBER, TICKET_CATEGORY);

        assertThat(result.getId(), is(notNullValue()));
        assertThat(result.getUserId(), is(USER_ID));
        assertThat(result.getEventId(), is(EVENT_ID));
        assertThat(result.getPlaceNumber(), is(PLACE_NUMBER));
        assertThat(result.getTicketCategory(), is(TICKET_CATEGORY));

        Ticket storedTicket = inMemoryStorage.getTicketInMemoryStorageMap().get(result.getId());
        assertThat(result, is(storedTicket));
        inMemoryStorage.getTicketInMemoryStorageMap().remove(result.getId());
    }

    @Test
    public void shouldGetAllTicketsByEvent() {
        Ticket ticket = new Ticket(TICKET_ID, USER_ID, EVENT_ID, PLACE_NUMBER, TICKET_CATEGORY);
        Event event = new Event(EVENT_ID, EVENT_DATE, EVENT_TITLE);
        inMemoryStorage.getTicketInMemoryStorageMap().put(TICKET_ID, ticket);

        List<Ticket> result = testInstance.getBookedTickets(event, PAGE_SIZE, PAGE_NUMBER);

        assertThat(result, hasSize(1));
        assertThat(result.get(0), is(ticket));
        inMemoryStorage.getTicketInMemoryStorageMap().remove(TICKET_ID);
    }

    @Test
    public void shouldGetAllTicketsByUser() {
        Ticket ticket = new Ticket(TICKET_ID, USER_ID, EVENT_ID, PLACE_NUMBER, TICKET_CATEGORY);
        User user = new User(USER_ID, USER_NAME, USER_EMAIL);
        inMemoryStorage.getTicketInMemoryStorageMap().put(TICKET_ID, ticket);

        List<Ticket> result = testInstance.getBookedTickets(user, PAGE_SIZE, PAGE_NUMBER);

        assertThat(result, hasSize(1));
        assertThat(result.get(0), is(ticket));
        inMemoryStorage.getTicketInMemoryStorageMap().remove(TICKET_ID);
    }

    @Test
    public void shouldCancelTicketById() {
        Ticket ticket = new Ticket(TICKET_ID, USER_ID, EVENT_ID, PLACE_NUMBER, TICKET_CATEGORY);
        inMemoryStorage.getTicketInMemoryStorageMap().put(TICKET_ID, ticket);

        boolean result = testInstance.cancelTicket(TICKET_ID);

        assertThat(result, is(Boolean.TRUE));
        Ticket storedTicket = inMemoryStorage.getTicketInMemoryStorageMap().get(TICKET_ID);
        assertThat(storedTicket, is(nullValue()));
    }
}


