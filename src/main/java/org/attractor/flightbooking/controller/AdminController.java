package org.attractor.flightbooking.controller;

import jakarta.validation.Valid;
import org.attractor.flightbooking.dto.CompanyCreationDto;
import org.attractor.flightbooking.dto.CompanyDto;
import org.attractor.flightbooking.dto.FlightDto;
import org.attractor.flightbooking.dto.UserDto;
import org.attractor.flightbooking.service.TicketService;
import org.attractor.flightbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public String dashboard(Model model, @RequestParam(defaultValue = "0") int page) {
        if (page < 0) {
            page = 0;
        }
        int pageSize = 5;
        Page<UserDto> companyPage = userService.findAllCompanies(page, pageSize);
        if (page >= companyPage.getTotalPages() && companyPage.getTotalPages() > 0) {
            page = companyPage.getTotalPages() - 1;
            companyPage = userService.findAllCompanies(page, 5);
        }
        model.addAttribute("companies", companyPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", companyPage.getTotalPages());
        model.addAttribute("totalItems", companyPage.getTotalElements());
        return "admin/dashboard";
    }

    @GetMapping("/company/create")
    public String showCreateCompanyForm(Model model) {
        model.addAttribute("companyForm", new CompanyCreationDto());
        return "admin/company-create";
    }

    @PostMapping("/company/create")
    public String createCompany(@Valid CompanyCreationDto companyDto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/company-create";
        }
        if (userService.existsByEmail(companyDto.getEmail())) {
            bindingResult.rejectValue("email", "error.companyForm", "Email already exists");
            return "admin/company-create";
        }
        try {
            userService.createCompany(companyDto);
            return "redirect:/admin?success=true";
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/company-create";
        }
    }

    @PostMapping("/company/{id}/toggle-freeze")
    public String toggleFreezeCompany(@PathVariable Long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal, RedirectAttributes redirect) {
        UserDto company = userService.findUserDtoById(id);
        if (company == null) {
            redirect.addFlashAttribute("error", "Company not found");
            return "redirect:/admin";
        }
        if (!company.getRoleNames().contains("COMPANY")) {
            redirect.addFlashAttribute("error", "Can only freeze/unfreeze companies");
            return "redirect:/admin";
        }
        if (ticketService.hasActiveBookings(id) && !company.isFrozen()) {
            redirect.addFlashAttribute("error", "Cannot freeze company with active bookings");
            return "redirect:/admin";
        }
        userService.toggleFreezeCompany(id);
        redirect.addFlashAttribute("success", true);
        return "redirect:/admin";
    }

    @GetMapping("/flights")
    public String viewFlights(Model model, @RequestParam(defaultValue = "0") int page) {
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 1);
        Page<CompanyDto> companyPage = userService.findAllCompaniesWithFlights(pageable);
        if (page >= companyPage.getTotalPages() && companyPage.getTotalPages() > 0) {
            page = companyPage.getTotalPages() - 1;
            pageable = PageRequest.of(page, 1);
            companyPage = userService.findAllCompaniesWithFlights(pageable);
        }

        for (CompanyDto company : companyPage.getContent()) {
            for (FlightDto flight : company.getFlights()) {
                flight.setCount(ticketService.countBookingsByFlightId(flight.getId()));
            }
        }

        model.addAttribute("companies", companyPage.getContent());
        model.addAttribute("currentPage", companyPage.getNumber());
        model.addAttribute("totalPages", companyPage.getTotalPages());
        model.addAttribute("totalItems", companyPage.getTotalElements());
        return "admin/flights";
    }
}