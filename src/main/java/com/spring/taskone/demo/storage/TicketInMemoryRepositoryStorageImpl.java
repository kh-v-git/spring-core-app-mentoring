/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.spring.taskone.demo.entities.Ticket;
import com.spring.taskone.demo.repository.TicketRepository;

public class TicketInMemoryRepositoryStorageImpl implements TicketRepository {
    private final Map<Long, Ticket> ticketStorageMap = new HashMap<>();

    @Override
    public Optional<Ticket> findById(final long id) {
        return Optional.ofNullable(ticketStorageMap.get(id));
    }

    @Override
    public Optional<Ticket> save(final Ticket ticket) {
        return Optional.ofNullable(ticketStorageMap.put(ticket.getId(), ticket));
    }

    @Override
    public void delete(final long id) {
        ticketStorageMap.remove(id);
    }

    @Override
    public boolean existsById(final long id) {
        return ticketStorageMap.containsKey(id);
    }
}
