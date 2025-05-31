package org.attractor.flightbooking.service.impl;

import org.attractor.flightbooking.dto.BookingDto;
import org.attractor.flightbooking.model.Booking;
import org.attractor.flightbooking.model.Ticket;
import org.attractor.flightbooking.model.User;
import org.attractor.flightbooking.repository.BookingRepository;
import org.attractor.flightbooking.service.BookingService;
import org.attractor.flightbooking.service.TicketService;
import org.attractor.flightbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Transactional
    @Override
    public void createBooking(BookingDto bookingDto, String userEmail) {
        User user = userService.findByEmail(userEmail);
        Ticket ticket = ticketService.findById(bookingDto.getTicketId());

        ticketService.updateTicketStatus(bookingDto.getTicketId(), true);

        Booking booking = Booking.builder()
                .user(user)
                .ticket(ticket)
                .bookingTime(LocalDateTime.now())
                .build();
        bookingRepository.save(booking);
    }
}