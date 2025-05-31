package org.attractor.flightbooking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingDto {
    @NotNull(message = "Ticket ID is required")
    private Long ticketId;
    @NotNull(message = "Flight ID is required")
    private Long flightId;
}