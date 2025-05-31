package org.attractor.flightbooking.service;

import org.attractor.flightbooking.dto.BookingDto;
import org.attractor.flightbooking.dto.TicketDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookingService {

    @Transactional
    void createBooking(BookingDto bookingDto, String userEmail);
}
