/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.facade;

import java.time.LocalDate;
import java.util.List;

import com.spring.taskone.demo.entities.Event;
import com.spring.taskone.demo.entities.Ticket;
import com.spring.taskone.demo.entities.User;
import com.spring.taskone.demo.utils.TicketCategoryEnum;

public class BookingFacadeImpl implements BookingFacade{

    @Override
    public Event getEventById(final long eventId) {
        return null;
    }

    @Override
    public List<Event> getEventsByTitle(final String title, final int pageSize, final int pageNum) {
        return null;
    }

    @Override
    public List<Event> getEventsForDay(final LocalDate day, final int pageSize, final int pageNum) {
        return null;
    }

    @Override
    public Event createEvent(final Event event) {
        return null;
    }

    @Override
    public Event updateEvent(final Event event) {
        return null;
    }

    @Override
    public boolean deleteEvent(final long eventId) {
        return false;
    }

    @Override
    public User getUserById(final long userId) {
        return null;
    }

    @Override
    public User getUserByEmail(final String email) {
        return null;
    }

    @Override
    public List<User> getUsersByName(final String name, final int pageSize, final int pageNum) {
        return null;
    }

    @Override
    public User createUser(final User user) {
        return null;
    }

    @Override
    public User updateUser(final User user) {
        return null;
    }

    @Override
    public boolean deleteUser(final long userId) {
        return false;
    }

    @Override
    public Ticket bookTicket(final long userId, final long eventId, final int place, final TicketCategoryEnum ticketCategory) {
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

    @Override
    public boolean cancelTicket(final long ticketId) {
        return false;
    }
}
