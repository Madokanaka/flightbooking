package org.attractor.flightbooking.service;

import org.attractor.flightbooking.dto.FlightDto;
import org.attractor.flightbooking.model.City;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    Page<FlightDto> findAllFlights(int page);

    Page<FlightDto> searchFlights(String departureCity, String arrivalCity, LocalDate departureDate, LocalDate returnDate, int page);

}
