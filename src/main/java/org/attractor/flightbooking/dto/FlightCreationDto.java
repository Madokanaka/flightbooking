package org.attractor.flightbooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FlightCreationDto {
    @NotBlank(message = "Departure city is required")
    private String departureCity;
    @NotBlank(message = "Arrival city is required")
    private String arrivalCity;
    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;
    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;
}