package com.spring.taskone.demo.service.ticket;

import java.util.List;
import java.util.Optional;

import com.spring.taskone.demo.entities.Event;
import com.spring.taskone.demo.entities.Ticket;
import com.spring.taskone.demo.entities.User;
import com.spring.taskone.demo.utils.TicketCategoryEnum;

public interface TicketService {
    Optional<Ticket> bookTicket(long userId, long eventId, int place, TicketCategoryEnum ticketCategory);

    List<Ticket> getBookedTickets(User user, int pageSize, int pageNum);

    List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum);

    boolean cancelTicket(long ticketId);
}
