package com.spring.taskone.demo.entities;

import java.util.Objects;

import com.spring.taskone.demo.utils.TicketCategoryEnum;

public class Ticket extends AbstractEntity {
    /**
     * Ticket Id. UNIQUE.
     */
    private long eventId;
    private long userId;
    private int placeNumber;
    private TicketCategoryEnum ticketCategory;

    public Ticket() {

    }

    public Ticket(final long userId, final long eventId, final int placeNumber, final TicketCategoryEnum ticketCategory) {
        this.userId = userId;
        this.eventId = eventId;
        this.placeNumber = placeNumber;
        this.ticketCategory = ticketCategory;
    }

    public Ticket(final long ticketId, final long userId, final long eventId, final int placeNumber, final TicketCategoryEnum ticketCategory) {
        this.id = ticketId;
        this.userId = userId;
        this.eventId = eventId;
        this.placeNumber = placeNumber;
        this.ticketCategory = ticketCategory;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(final long eventId) {
        this.eventId = eventId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(final int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public TicketCategoryEnum getTicketCategory() {
        return ticketCategory;
    }

    public void setTicketCategory(final TicketCategoryEnum ticketCategory) {
        this.ticketCategory = ticketCategory;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket)) {
            return false;
        }
        final Ticket ticket = (Ticket) o;
        return id == ticket.id && eventId == ticket.eventId && userId == ticket.userId && placeNumber == ticket.placeNumber && ticketCategory == ticket.ticketCategory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventId, userId, placeNumber, ticketCategory);
    }

    @Override
    public String toString() {
        return "Ticket{" + "eventId=" + eventId + ", userId=" + userId + ", placeNumber=" + placeNumber + ", ticketCategory=" + ticketCategory + '}';
    }
}
