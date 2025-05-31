package org.attractor.flightbooking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.attractor.flightbooking.model.City;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightDto {
    private Long id;
    @NotNull(message = "Flight number is required")
    private String flightNumber;
    @NotNull(message = "Departure city is required")
    private String departureCity;
    @NotNull(message = "Arrival city is required")
    private String arrivalCity;
    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;
    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;
    @NotNull(message = "Company is required")
    private CompanyDto company;
    private List<TicketDto> tickets;

    @Data
    @Builder
    public static class CompanyDto {
        private Long id;
        @NotNull(message = "Company name is required")
        private String name;
        private String logoPath;
    }
}