package com.railiac.rest.rest.controller;

import com.railiac.rest.database.model.Ticket;
import com.railiac.rest.database.model.User;
import com.railiac.rest.database.repository.TicketRepository;
import com.railiac.rest.database.repository.UserRepository;
import com.railiac.rest.rest.exception.TicketException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/fetch/{ticketId}")
    private Ticket getTicket(@PathVariable Long ticketId) {
        return this.ticketRepository.findById(ticketId).orElse(null);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/ocr/{ticketId}")
    private User ocrTicket(@PathVariable Long ticketId) throws TicketException {
        Ticket ticket = this.ticketRepository.findById(ticketId).orElseThrow(() -> new TicketException("No ticket found"));
        return null; // TODO <--k

        //return: JSON with extracted ticket information
        //send the uploaded PDF or JPEG to the OCR and return the extracted data (hopefully ;-))
    }

}
