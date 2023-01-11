package com.spring.taskone.demo.service.ticket;

import java.util.List;
import java.util.Optional;

import com.spring.taskone.demo.entities.Event;
import com.spring.taskone.demo.entities.Ticket;
import com.spring.taskone.demo.entities.User;
import com.spring.taskone.demo.repository.impl.TicketRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TicketServiceImpl implements TicketService {
    private static final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketRepositoryImpl ticketRepositoryImpl;

    @Override
    public Ticket bookTicket(Ticket ticket) {
        Ticket savedTicket = ticketRepositoryImpl.save(ticket);

        log.debug("New Ticket with ID {} was created.", savedTicket.getId());
        return savedTicket;
    }

    @Override
    public List<Ticket> getBookedTickets(final User user, final int pageSize, final int pageNum) {
        return ticketRepositoryImpl.getBookedTickets(user, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTickets(final Event event, final int pageSize, final int pageNum) {
        return ticketRepositoryImpl.getBookedTickets(event, pageSize, pageNum);
    }

    @Override
    public boolean cancelTicket(final long ticketId) {
        Optional<Ticket> maybeTicket = ticketRepositoryImpl.findById(ticketId);
        if (maybeTicket.isEmpty()) {
            log.debug("Ticket with ID {} was NOT deleted cause NOT found.", ticketId);

            return false;
        }
        ticketRepositoryImpl.delete(maybeTicket.get());

        log.debug("Ticket with ID {} was deleted.", ticketId);

        return true;
    }
}
