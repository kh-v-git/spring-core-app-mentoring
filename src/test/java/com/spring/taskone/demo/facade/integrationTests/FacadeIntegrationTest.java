/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2023. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.facade.integrationTests;

import java.time.LocalDate;

import com.spring.taskone.demo.entities.Event;
import com.spring.taskone.demo.entities.Ticket;
import com.spring.taskone.demo.entities.User;
import com.spring.taskone.demo.facade.BookingFacade;
import com.spring.taskone.demo.utils.TicketCategoryEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest
public class FacadeIntegrationTest {
    @Autowired
    BookingFacade testInstance;

    @Test
    public void shouldCreateEventFromFacade() throws Exception {
        Event event = new Event(LocalDate.parse("2022-09-01"), "Test Event");

        Event testEvent = testInstance.createEvent(event);

        assertThat(testEvent.getDate(), is(event.getDate()));
        assertThat(testEvent.getTitle(), is(event.getTitle()));
    }

    @Test
    public void shouldCreateUserFromFacade() throws Exception {
        User user = new User("Bob Tester", "bob.test@gmail.com");

        User testUser = testInstance.createUser(user);

        assertThat(testUser.getName(), is(user.getName()));
        assertThat(testUser.getEmail(), is(user.getEmail()));
    }

    @Test
    public void shouldCreateTicketFromFacade() throws Exception {
        long eventId = 10L;
        long userId = 20L;
        int placeNumber = 10;
        TicketCategoryEnum categoryTicket = TicketCategoryEnum.PREMIUM;

        Ticket testTicket = testInstance.bookTicket(eventId, userId, placeNumber, categoryTicket);

    }

}
