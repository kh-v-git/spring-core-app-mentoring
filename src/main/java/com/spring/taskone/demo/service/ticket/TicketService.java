package com.spring.taskone.demo.service.ticket;

import java.util.List;

import com.spring.taskone.demo.entities.Event;
import com.spring.taskone.demo.entities.Ticket;
import com.spring.taskone.demo.entities.User;

public interface TicketService {
    Ticket bookTicket(Ticket ticket);

    List<Ticket> getBookedTickets(User user, int pageSize, int pageNum);

    List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum);

    boolean cancelTicket(long ticketId);
}
