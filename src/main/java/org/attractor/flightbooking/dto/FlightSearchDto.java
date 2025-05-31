package org.attractor.flightbooking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.attractor.flightbooking.model.City;
import org.attractor.flightbooking.validation.ValidCity;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightSearchDto {
    @ValidCity
    private String departureCity;
    @ValidCity
    private String arrivalCity;
    @NotNull(message = "Departure date is required")
    private LocalDate departureDate;
    @NotNull(message = "Return date is required")
    private LocalDate returnDate;
}