/*
 * Canadian Tire Corporation, Ltd. Do not reproduce without permission in writing.
 * Copyright (c) 2022. Canadian Tire Corporation, Ltd. All rights reserved.
 */
package com.spring.taskone.demo.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Event {
    /**
     * Event id. UNIQUE.
     *
     * @return Event Id
     */
    private long id;
    private LocalDate date;
    private String title;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        final Event event = (Event) o;
        return id == event.id && date.equals(event.date) && title.equals(event.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, title);
    }

    @Override
    public String toString() {
        return "Event{" + "date=" + date + ", title='" + title + '\'' + '}';
    }
}
