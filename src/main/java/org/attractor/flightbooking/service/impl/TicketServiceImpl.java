package org.attractor.flightbooking.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.attractor.flightbooking.model.Ticket;
import org.attractor.flightbooking.repository.TicketRepository;
import org.attractor.flightbooking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket findById(Long ticketId) {
        log.info("Fetching ticket by id={}", ticketId);
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
    }

    @Override
    public void updateTicketStatus(Long ticketId, boolean isBooked) {
        log.info("Updating ticket status for ticketId={} to isBooked={}", ticketId, isBooked);
        Ticket ticket = findById(ticketId);
        if (ticket.isBooked() && isBooked) {
            throw new IllegalStateException("Ticket is already booked");
        }
        ticket.setBooked(isBooked);
        ticketRepository.save(ticket);
    }

    @Override
    public long countBookingsByFlightId(Long flightId) {
        return ticketRepository.countByFlightIdAndIsBookedTrue(flightId);
    }

    @Override
    public boolean hasActiveBookings(Long companyId) {
        log.info("Checking if companyId={} has active bookings", companyId);
        return ticketRepository.existsByFlightCompanyIdAndIsBookedTrue(companyId);
    }}