package org.attractor.flightbooking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.attractor.flightbooking.dto.UserDto;
import org.attractor.flightbooking.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "auth/registration";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model, @RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "auth/login";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserDto userDto,
                               BindingResult bindingResult,
                               Model model) {
        if (userService.existsByEmail(userDto.getEmail())) {
            bindingResult.rejectValue("email", "email.exists", "User with this email was already registered");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("userDto", userDto);
            return "auth/registration";
        }

        userService.registerUser(userDto);
        return "redirect:/auth/login";
    }

}
