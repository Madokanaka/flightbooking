package org.attractor.flightbooking.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.flightbooking.dto.FlightCreationDto;
import org.attractor.flightbooking.dto.FlightDto;
import org.attractor.flightbooking.dto.TicketDto;
import org.attractor.flightbooking.model.Flight;
import org.attractor.flightbooking.model.Ticket;
import org.attractor.flightbooking.model.User;
import org.attractor.flightbooking.repository.FlightRepository;
import org.attractor.flightbooking.service.FlightService;
import org.attractor.flightbooking.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final UserService userService;

    @Override
    public Page<FlightDto> findAllFlights(int page) {
        log.info("Fetching all flights");
        return flightRepository.findAll(PageRequest.of(page - 1, 10))
                .map(this::toDto);
    }

    @Override
    public Page<FlightDto> searchFlights(String departureCity, String arrivalCity, LocalDate departureDate, LocalDate returnDate, int page) {
        log.info("Searching for flights from {} to {} on dates {} and {}", departureCity, arrivalCity, departureDate, returnDate);
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
        log.info("Converting flight={} to FlightDto", flight);
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

    @Transactional
    @Override
    public void createFlight(FlightCreationDto flightDto, String companyEmail) {
        log.info("Creating flight with");
        User company = userService.findByEmail(companyEmail);

        String flightNumber;
        do {
            flightNumber = "FL" + String.format("%04d", new Random().nextInt(10000));
        } while (flightRepository.existsByFlightNumber(flightNumber));

        Flight flight = new Flight();
        flight.setFlightNumber(flightNumber);
        flight.setDepartureCity(flightDto.getDepartureCity());
        flight.setArrivalCity(flightDto.getArrivalCity());
        flight.setDepartureTime(flightDto.getDepartureTime());
        flight.setArrivalTime(flightDto.getArrivalTime());
        flight.setCompany(company);

        List<Ticket> tickets = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            Ticket ticket = new Ticket();
            ticket.setSeatNumber("E" + i);
            ticket.setTicketClass("Economy");
            ticket.setPrice(100.0);
            ticket.setBooked(false);
            ticket.setFlight(flight);
            tickets.add(ticket);
        }
        for (int i = 1; i <= 3; i++) {
            Ticket ticket = new Ticket();
            ticket.setSeatNumber("B" + i);
            ticket.setTicketClass("Business");
            ticket.setPrice(200.0);
            ticket.setBooked(false);
            ticket.setFlight(flight);
            tickets.add(ticket);
        }
        flight.setTickets(tickets);

        flightRepository.save(flight);
    }

    @Override
    public Page<FlightDto> getCompanyFlights(String companyEmail, Pageable pageable) {
        log.info("Fetching company flights for company with email={}", companyEmail);
        User company = userService.findByEmail(companyEmail);

        Page<Flight> flights = flightRepository.findByCompanyId(company.getId(), pageable);
        return flights.map(flight -> {
            List<TicketDto> ticketDtos = flight.getTickets().stream()
                    .map(ticket -> TicketDto.builder()
                            .id(ticket.getId())
                            .seatNumber(ticket.getSeatNumber())
                            .ticketClass(ticket.getTicketClass())
                            .price(ticket.getPrice())
                            .isBooked(ticket.isBooked())
                            .build())
                    .toList();
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
                    .tickets(ticketDtos)
                    .build();
        });
    }
}