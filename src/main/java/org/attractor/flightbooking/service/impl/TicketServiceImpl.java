package org.attractor.flightbooking.service.impl;

import org.attractor.flightbooking.model.Ticket;
import org.attractor.flightbooking.repository.TicketRepository;
import org.attractor.flightbooking.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket findById(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found"));
    }

    @Override
    public void updateTicketStatus(Long ticketId, boolean isBooked) {
        Ticket ticket = findById(ticketId);
        if (ticket.isBooked() && isBooked) {
            throw new IllegalStateException("Ticket is already booked");
        }
        ticket.setBooked(isBooked);
        ticketRepository.save(ticket);
    }

    @Override
    public long countBookingsByCompanyId(Long companyId) {
        return ticketRepository.countByFlightCompanyIdAndIsBookedTrue(companyId);
    }

    @Override
    public boolean hasActiveBookings(Long companyId) {
        return ticketRepository.existsByFlightCompanyIdAndIsBookedTrue(companyId);
    }}