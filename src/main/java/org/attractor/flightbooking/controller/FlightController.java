package org.attractor.flightbooking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.attractor.flightbooking.dto.FlightDto;
import org.attractor.flightbooking.dto.FlightSearchDto;
import org.attractor.flightbooking.model.City;
import org.attractor.flightbooking.service.FlightService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @GetMapping
    public String showFlightsPage(Model model, @RequestParam(defaultValue = "1") int page) {
        if (page < 1) {
            page = 1;
        }

        Page<FlightDto> flights = flightService.findAllFlights(page);
        if (page > flights.getTotalPages() && flights.getTotalPages() > 0) {
            page = flights.getTotalPages();
            flights = flightService.findAllFlights(page);
        }
        model.addAttribute("flights", flights.getContent());
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", flights.getTotalPages());
        model.addAttribute("cityOptions", Arrays.stream(City.values())
                .collect(Collectors.toMap(City::name, City::getDisplayName, (a, b) -> a, LinkedHashMap::new)));
        model.addAttribute("searchDto", new FlightSearchDto());
        return "flights/flight";
    }

    @GetMapping("/search")
    public String searchFlights(
            @Valid @ModelAttribute("searchDto") FlightSearchDto searchDto,
            BindingResult bindingResult,
            @RequestParam(defaultValue = "1") int page,
            Model model) {
        if (page < 1) {
            page = 1;
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("flights", null);
            model.addAttribute("currentPage", 1);
            model.addAttribute("totalPages", 1);
            if(searchDto.getDepartureDate() == null) {
                model.addAttribute("departureDateError", "Departure date is required");
            };
            if (searchDto.getReturnDate() == null) {
                model.addAttribute("returnDateError", "Return date is required");
            }
            model.addAttribute("cityOptions", Arrays.stream(City.values())
                    .collect(Collectors.toMap(City::name, City::getDisplayName, (a, b) -> a, LinkedHashMap::new)));
            model.addAttribute("searchDto", searchDto);
            return "flights/flight";
        }

        Page<FlightDto> flights = flightService.searchFlights(
                searchDto.getDepartureCity(),
                searchDto.getArrivalCity(),
                searchDto.getDepartureDate(),
                searchDto.getReturnDate(),
                page);

        if (page > flights.getTotalPages() && flights.getTotalPages() > 0) {
            page = flights.getTotalPages();
            flights = flightService.searchFlights(
                    searchDto.getDepartureCity(),
                    searchDto.getArrivalCity(),
                    searchDto.getDepartureDate(),
                    searchDto.getReturnDate(),
                    page);
        }

        model.addAttribute("flights", flights.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", flights.getTotalPages());
        model.addAttribute("cityOptions", Arrays.stream(City.values())
                .collect(Collectors.toMap(City::name, City::getDisplayName, (a, b) -> a, LinkedHashMap::new)));
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("searchPerformed", true);
        return "flights/flight";
    }
}