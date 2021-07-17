package com.railiac.rest.rest.exception;

import com.railiac.rest.database.model.Ticket;

import java.util.function.Supplier;

public class TicketException extends Exception {
    public TicketException(String message) {
        super(message);
    }
}
