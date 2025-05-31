package org.attractor.flightbooking.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.flightbooking.dto.CompanyCreationDto;
import org.attractor.flightbooking.dto.CompanyDto;
import org.attractor.flightbooking.dto.FlightDto;
import org.attractor.flightbooking.dto.TicketDto;
import org.attractor.flightbooking.dto.UserDto;
import org.attractor.flightbooking.exception.ResourceNotFoundException;
import org.attractor.flightbooking.exception.UserNotFoundException;
import org.attractor.flightbooking.model.Role;
import org.attractor.flightbooking.model.User;
import org.attractor.flightbooking.repository.UserRepository;
import org.attractor.flightbooking.service.RoleService;
import org.attractor.flightbooking.service.TicketService;
import org.attractor.flightbooking.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final TicketService ticketService;

    @Override
    @Transactional
    public void registerUser(UserDto userDto) {
        User user = User.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .isFrozen(false)
                .build();

        Role userRole = roleService.findRoleByName("USER");
        user.setRoles(List.of(userRole));

        userRepository.save(user);
    }
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserDto findUserDtoByEmail(String email) {
        log.info("Fetching user by email={}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setLogoPath(user.getLogoPath());
        return userDto;
    }

    @Override
    public void saveAvatar(String email, String fileName) {
        log.info("Saving image for userId={}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setLogoPath(fileName);
        userRepository.save(user);

        log.debug("Image saved with fileName={}", fileName);
    }

    @Transactional
    @Override
    public void createCompany(CompanyCreationDto companyDto) {
        log.info("Creating company with name={}", companyDto.getName());
        User company = new User();
        company.setName(companyDto.getName());
        company.setEmail(companyDto.getEmail());
        company.setPassword(passwordEncoder.encode(companyDto.getPassword()));
        company.setIsFrozen(false);
        Role companyRole = roleService.findRoleByName("COMPANY");
        company.setRoles(Collections.singletonList(companyRole));
        userRepository.save(company);
    }

    @Transactional
    @Override
    public void toggleFreezeCompany(Long companyId) {
        log.info("Toggling freeze for company with id={}", companyId);
        User company = userRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        if (!company.getRoles().stream().anyMatch(role -> role.getRoleName().equals("COMPANY"))) {
            throw new ResourceNotFoundException("Can only freeze/unfreeze companies");
        }
        company.setIsFrozen(!company.getIsFrozen());
        userRepository.save(company);
    }

    @Override
    public UserDto findUserDtoById(Long id) {
        log.info("Fetching user by id={}", id);

        User user =  userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        return toUserDto(user);
    }

    private CompanyDto toCompanyDto(User user) {
        CompanyDto dto = new CompanyDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setFrozen(user.getIsFrozen());
        dto.setLogoPath(user.getLogoPath());
        dto.setFlights(user.getFlights().stream()
                .map(this::toFlightDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private FlightDto toFlightDto(org.attractor.flightbooking.model.Flight flight) {
        FlightDto dto = new FlightDto();
        dto.setId(flight.getId());
        dto.setFlightNumber(flight.getFlightNumber());
        dto.setDepartureCity(flight.getDepartureCity());
        dto.setArrivalCity(flight.getArrivalCity());
        dto.setDepartureTime(flight.getDepartureTime());
        dto.setArrivalTime(flight.getArrivalTime());
        dto.setCompany((FlightDto.CompanyDto.builder()
                .id(flight.getCompany().getId())
                .name(flight.getCompany().getName())
                .logoPath(flight.getCompany().getLogoPath())
                .build()));
        dto.setTickets(flight.getTickets().stream()
                .map(this::toTicketDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private TicketDto toTicketDto(org.attractor.flightbooking.model.Ticket ticket) {
        TicketDto dto = new TicketDto();
        dto.setId(ticket.getId());
        dto.setSeatNumber(ticket.getSeatNumber());
        dto.setTicketClass(ticket.getTicketClass());
        dto.setPrice(ticket.getPrice());
        dto.setBooked(ticket.isBooked());
        return dto;
    }

    @Override
    public Page<UserDto> findAllCompanies(int page, int size) {
        log.info("Fetching companies with pagination: page {}, size {}", page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return userRepository.findByRolesRoleName("COMPANY", pageable)
                .map(this::toUserDto);
    }

    @Override
    public Page<CompanyDto> findAllCompaniesWithFlights(Pageable pageable) {
        log.info("Fetching all companies with flights");
        return userRepository.findByRolesRoleName("COMPANY", pageable)
                .map(this::toCompanyDto);
    }

    private UserDto toUserDto(User user) {
        log.info("Converting user={} to UserDto", user);
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setFrozen(user.getIsFrozen());
        dto.setLogoPath(user.getLogoPath());
        dto.setRoleNames(user.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList()));
        return dto;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}