package org.attractor.flightbooking.service;

import org.attractor.flightbooking.dto.FlightCreationDto;
import org.attractor.flightbooking.dto.FlightDto;
import org.attractor.flightbooking.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    Page<FlightDto> findAllFlights(int page);

    Page<FlightDto> searchFlights(String departureCity, String arrivalCity, LocalDate departureDate, LocalDate returnDate, int page);

    @Transactional
    void createFlight(FlightCreationDto flightDto, String companyEmail);

    Page<FlightDto> getCompanyFlights(String companyEmail, Pageable pageable);
}
