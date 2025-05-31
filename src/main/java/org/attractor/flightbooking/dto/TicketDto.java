package org.attractor.flightbooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private Long id;
    @NotBlank(message = "Seat number is required")
    private String seatNumber;
    @NotBlank(message = "Ticket class is required")
    private String ticketClass;
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;
    private boolean isBooked;
}