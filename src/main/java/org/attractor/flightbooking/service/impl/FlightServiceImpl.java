package org.attractor.flightbooking.service.impl;

import lombok.RequiredArgsConstructor;
import org.attractor.flightbooking.dto.FlightDto;
import org.attractor.flightbooking.dto.TicketDto;
import org.attractor.flightbooking.model.Flight;
import org.attractor.flightbooking.repository.FlightRepository;
import org.attractor.flightbooking.service.FlightService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;

    @Override
    public Page<FlightDto> findAllFlights(int page) {
        return flightRepository.findAll(PageRequest.of(page - 1, 10))
                .map(this::toDto);
    }

    @Override
    public Page<FlightDto> searchFlights(String departureCity, String arrivalCity, LocalDate departureDate, LocalDate returnDate, int page) {
        String departureCityStr = departureCity !=null ?departureCity.toUpperCase(): null;
        String arrivalCityStr = arrivalCity != null?arrivalCity.toUpperCase(): null;
        return flightRepository.findByFilters(
                departureCityStr,
                arrivalCityStr,
                departureDate.atStartOfDay(),
                returnDate.atStartOfDay(),
                PageRequest.of(page - 1, 10)).map(this::toDto);
    }


    private FlightDto toDto(Flight flight) {
        return FlightDto.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .departureCity(flight.getDepartureCity())
                .arrivalCity(flight.getArrivalCity())
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .company(FlightDto.CompanyDto.builder()
                        .id(flight.getCompany().getId())
                        .name(flight.getCompany().getName())
                        .logoPath(flight.getCompany().getLogoPath())
                        .build())
                .tickets(flight.getTickets().stream()
                        .map(ticket -> TicketDto.builder()
                                .id(ticket.getId())
                                .seatNumber(ticket.getSeatNumber())
                                .ticketClass(ticket.getTicketClass())
                                .price(ticket.getPrice())
                                .isBooked(ticket.isBooked())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}