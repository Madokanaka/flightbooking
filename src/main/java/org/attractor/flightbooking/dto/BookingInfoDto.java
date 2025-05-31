package org.attractor.flightbooking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingInfoDto {
    @NotNull(message = "Booking ID is required")
    private Long bookingId;

    @NotNull(message = "Flight number is required")
    private String flightNumber;

    @NotNull(message = "Departure city is required")
    private String departureCity;

    @NotNull(message = "Arrival city is required")
    private String arrivalCity;

    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;

    @NotNull(message = "Seat number is required")
    private String seatNumber;

    @NotNull(message = "Ticket class is required")
    private String ticketClass;

    @NotNull(message = "Price is required")
    private Double price;
}