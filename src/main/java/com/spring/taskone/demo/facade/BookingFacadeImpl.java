/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.facade;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.spring.taskone.demo.entities.Event;
import com.spring.taskone.demo.entities.Ticket;
import com.spring.taskone.demo.entities.User;
import com.spring.taskone.demo.service.event.EventService;
import com.spring.taskone.demo.service.ticket.TicketService;
import com.spring.taskone.demo.service.user.UserService;
import com.spring.taskone.demo.utils.TicketCategoryEnum;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class BookingFacadeImpl implements BookingFacade {
    private final EventService eventService;
    private final TicketService ticketService;
    private final UserService userService;

    public BookingFacadeImpl(EventService eventService, TicketService ticketService, UserService userService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @Override
    public Event getEventById(final long eventId) {
        Optional<Event> maybeEvent = eventService.getEventById(eventId);
        return maybeEvent.orElse(null);
    }

    @Override
    public List<Event> getEventsByTitle(final String title, final int pageSize, final int pageNum) {
        return eventService.getEventsByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(final LocalDate day, final int pageSize, final int pageNum) {
        return eventService.getEventsForDay(day, pageSize, pageNum);
    }

    @Override
    public Event createEvent(final Event event) {
        return eventService.createEvent(event).orElse(null);
    }

    @Override
    public Event updateEvent(final Event event) {
        return eventService.updateEvent(event).orElse(null);
    }

    @Override
    public boolean deleteEvent(final long eventId) {
        return eventService.deleteEvent(eventId);
    }

    @Override
    public User getUserById(final long userId) {
        return userService.getUserById(userId).orElse(null);
    }

    @Override
    public User getUserByEmail(final String email) {
        return userService.getUserByEmail(email).orElse(null);
    }

    @Override
    public List<User> getUsersByName(final String name, final int pageSize, final int pageNum) {
        return userService.getUsersByName(name, pageSize, pageNum);
    }

    @Override
    public User createUser(final User user) {
        return userService.createUser(user).orElse(null);
    }

    @Override
    public User updateUser(final User user) {
        return userService.updateUser(user).orElse(null);
    }

    @Override
    public boolean deleteUser(final long userId) {
        return userService.deleteUser(userId);
    }

    @Override
    public Ticket bookTicket(final long userId, final long eventId, final int place, final TicketCategoryEnum ticketCategory) {
        return ticketService.bookTicket(userId, eventId, place, ticketCategory).orElse(null);
    }

    @Override
    public List<Ticket> getBookedTickets(final User user, final int pageSize, final int pageNum) {
        return ticketService.getBookedTickets(user, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTickets(final Event event, final int pageSize, final int pageNum) {
        return ticketService.getBookedTickets(event, pageSize, pageNum);
    }

    @Override
    public boolean cancelTicket(final long ticketId) {
        return ticketService.cancelTicket(ticketId);
    }
}
