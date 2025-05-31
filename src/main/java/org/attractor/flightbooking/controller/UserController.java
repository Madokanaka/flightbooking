package org.attractor.flightbooking.controller;

import org.attractor.flightbooking.dto.BookingInfoDto;
import org.attractor.flightbooking.dto.UserDto;
import org.attractor.flightbooking.model.City;
import org.attractor.flightbooking.service.BookingService;
import org.attractor.flightbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@Controller
@RequestMapping()
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @GetMapping(value = "/profile")
    public String showProfile(@RequestParam(defaultValue = "1") int page, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto user = userService.findUserDtoByEmail(email);
        Page<BookingInfoDto> bookingPage = bookingService.getUserBookingInfo(email, PageRequest.of(page - 1, 5));

        model.addAttribute("user", user);
        model.addAttribute("bookings", bookingPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookingPage.getTotalPages());
        model.addAttribute("cityOptions", Arrays.stream(City.values()));
        return "profile";
    }
}