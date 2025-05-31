package org.attractor.flightbooking.controller;

import jakarta.validation.Valid;
import org.attractor.flightbooking.dto.BookingDto;
import org.attractor.flightbooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<String> bookTicket(@Valid @RequestBody BookingDto bookingDto,
                                             Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("redirect:/login");
        }

        if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN") ||
                        auth.getAuthority().equals("ROLE_COMPANY"))) {
            return ResponseEntity.status(403).body("Admins and companies cannot book tickets");
        }

        try {
            bookingService.createBooking(bookingDto, authentication.getName());
            return ResponseEntity.ok("Booking successful");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}