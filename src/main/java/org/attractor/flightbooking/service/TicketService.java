package org.attractor.flightbooking.service;

import org.attractor.flightbooking.dto.TicketDto;
import org.attractor.flightbooking.model.Ticket;

import java.util.List;

public interface TicketService {

    Ticket findById(Long ticketId);

    void updateTicketStatus(Long ticketId, boolean isBooked);


    long countBookingsByFlightId(Long flightId);

    boolean hasActiveBookings(Long companyId);
}