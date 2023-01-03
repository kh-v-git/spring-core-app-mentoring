/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.service.ticket;

import java.util.List;
import java.util.Optional;

import com.spring.taskone.demo.entities.Event;
import com.spring.taskone.demo.entities.Ticket;
import com.spring.taskone.demo.entities.User;
import com.spring.taskone.demo.storage.InMemoryRepositoryStorageImpl;
import com.spring.taskone.demo.utils.TicketCategoryEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TicketServiceImpl implements TicketService {
    private static final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private InMemoryRepositoryStorageImpl inMemoryRepositoryStorage;

    @Override
    public Optional<Ticket> bookTicket(final long userId, final long eventId, final int place, final TicketCategoryEnum ticketCategory) {
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
