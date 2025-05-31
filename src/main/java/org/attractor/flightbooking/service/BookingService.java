package org.attractor.flightbooking.service;

import org.attractor.flightbooking.dto.BookingDto;
import org.attractor.flightbooking.dto.BookingInfoDto;
import org.attractor.flightbooking.dto.TicketDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookingService {

    @Transactional
    void createBooking(BookingDto bookingDto, String userEmail);

    Page<BookingInfoDto> getUserBookingInfo(String userEmail, Pageable pageable);
}
