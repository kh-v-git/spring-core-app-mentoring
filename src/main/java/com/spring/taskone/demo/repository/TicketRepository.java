/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.repository;

import java.util.Optional;

import com.spring.taskone.demo.entities.Ticket;

public interface TicketRepository {
    Optional<Ticket> findById(long id);

    Optional<Ticket> save(Ticket ticket);

    void delete(long id);

    boolean existsById(long id);
}
