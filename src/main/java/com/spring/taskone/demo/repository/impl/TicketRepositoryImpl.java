package com.spring.taskone.demo.repository.impl;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.spring.taskone.demo.entities.Event;
import com.spring.taskone.demo.entities.Ticket;
import com.spring.taskone.demo.entities.User;
import com.spring.taskone.demo.repository.TicketRepository;
import com.spring.taskone.demo.storage.InMemoryStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TicketRepositoryImpl implements TicketRepository {
    private static final Logger log = LoggerFactory.getLogger(TicketRepositoryImpl.class);

    private final InMemoryStorage inMemoryStorage;

    @Autowired
    public TicketRepositoryImpl(InMemoryStorage inMemoryStorage) {
        this.inMemoryStorage = inMemoryStorage;
    }

    @Override
    public Optional<Ticket> findById(final Long primaryKey) {
        return Optional.ofNullable(inMemoryStorage.getTicketInMemoryStorageMap().get(primaryKey));
    }

    @Override
    public boolean existsById(final Long primaryKey) {
        return inMemoryStorage.getTicketInMemoryStorageMap().containsKey(primaryKey);
    }

    @Override
    public Iterable<Ticket> findAll() {
        return inMemoryStorage.getTicketInMemoryStorageMap().values().stream().toList();
    }

    @Override
    public long count() {
        return inMemoryStorage.getTicketInMemoryStorageMap().size();
    }

    @Override
    public void delete(final Ticket entity) {
        inMemoryStorage.getTicketInMemoryStorageMap().remove(entity.getId());
    }

    @Override
    public <S extends Ticket> S save(final S entity) {
        if (entity.getId() == null) {

            Long maxId = inMemoryStorage.getTicketInMemoryStorageMap().keySet().stream().max(Long::compare).orElse(0L);
            entity.setId(maxId + 1);
            inMemoryStorage.getTicketInMemoryStorageMap().put(entity.getId(), entity);

            log.debug("New Ticket saved to repository with ID: {}", entity.getId());

            return entity;
        }

        inMemoryStorage.getTicketInMemoryStorageMap().put(entity.getId(), entity);
        log.debug("Ticket updated in repository with ID: {}", entity.getId());

        return entity;
    }

    @Override
    public List<Ticket> getBookedTickets(final User user, final int pageSize, final int pageNum) {
        var ticketList = inMemoryStorage.getTicketInMemoryStorageMap().values().stream().filter(ticket -> ticket.getUserId() == user.getId()).toList();

        return getTickets(pageSize, pageNum, ticketList);
    }

    @Override
    public List<Ticket> getBookedTickets(final Event event, final int pageSize, final int pageNum) {
        var ticketList = inMemoryStorage.getTicketInMemoryStorageMap().values().stream().filter(ticket -> ticket.getEventId() == event.getId()).toList();

        return getTickets(pageSize, pageNum, ticketList);
    }

    private List<Ticket> getTickets(final int pageSize, final int pageNum, final List<Ticket> ticketList) {
        int ticketListSize = ticketList.size();
        if (ticketListSize / pageSize <= 1) {
            return ticketList;
        }
        if (ticketListSize / pageSize < pageNum) {
            return Lists.newArrayList();
        }

        return ticketList.subList((pageNum - 1) * pageSize, pageNum * pageSize - 1);
    }
}
