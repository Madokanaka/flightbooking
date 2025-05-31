package org.attractor.flightbooking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDto {
    private Long bookingId;

    @NotNull(message = "Ticket ID is required")
    private Long ticketId;

    @NotNull(message = "Flight ID is required")
    private Long flightId;

    private String flightNumber;

    private String departureCity;

    private String arrivalCity;

    private LocalDateTime departureTime;

    private String seatNumber;

    private String ticketClass;

    private Double price;
}