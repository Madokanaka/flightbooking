package org.attractor.flightbooking.controller;

import jakarta.validation.Valid;
import org.attractor.flightbooking.dto.FlightCreationDto;
import org.attractor.flightbooking.dto.FlightDto;
import org.attractor.flightbooking.model.City;
import org.attractor.flightbooking.service.FlightService;
import org.attractor.flightbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private UserService userService;

    @Autowired
    private FlightService flightService;

    @GetMapping
    public String showCompanyProfile(@RequestParam(defaultValue = "1") int page, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<FlightDto> flightPage = flightService.getCompanyFlights(email, PageRequest.of(page - 1, 5));

        Map<String, String> cityOptions = Arrays.stream(City.values())
                .collect(Collectors.toMap(City::name, City::getDisplayName));

        model.addAttribute("company", userService.findUserDtoByEmail(email));
        model.addAttribute("flights", flightPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", flightPage.getTotalPages());
        model.addAttribute("cityOptions", cityOptions);
        return "company";
    }

    @GetMapping("/create")
    public String showFlightCreationForm(Model model) {
        Map<String, String> cityOptions = Arrays.stream(City.values())
                .collect(Collectors.toMap(City::name, City::getDisplayName));
        model.addAttribute("flightForm", FlightCreationDto.builder().build());
        model.addAttribute("cityOptions", cityOptions);
        return "flight-create";
    }

    @PostMapping("/create")
    public String createFlight(@Valid @ModelAttribute("flightForm") FlightCreationDto flightDto,
                               BindingResult bindingResult,
                               Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (flightDto.getDepartureCity() != null && flightDto.getDepartureCity().equals(flightDto.getArrivalCity())) {
            bindingResult.rejectValue("arrivalCity", "error.flightForm", "Departure and arrival cities cannot be the same");
        }
        if (flightDto.getDepartureTime() != null && flightDto.getArrivalTime() != null) {
            LocalDateTime now = LocalDateTime.now();
            if (flightDto.getDepartureTime().isBefore(now) || flightDto.getDepartureTime().isEqual(now)) {
                bindingResult.rejectValue("departureTime", "error.flightForm", "Departure time must be in the future");
            }
            if (flightDto.getArrivalTime().isBefore(now) || flightDto.getArrivalTime().isEqual(now)) {
                bindingResult.rejectValue("arrivalTime", "error.flightForm", "Arrival time must be in the future");
            }
            if (!flightDto.getDepartureTime().isBefore(flightDto.getArrivalTime())) {
                bindingResult.rejectValue("arrivalTime", "error.flightForm", "Arrival time must be after departure time");
            }
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> cityOptions = Arrays.stream(City.values())
                    .collect(Collectors.toMap(City::name, City::getDisplayName));
            model.addAttribute("cityOptions", cityOptions);
            return "flight-create";
        }

        flightService.createFlight(flightDto, email);
        return "redirect:/company?success=true";
    }
}