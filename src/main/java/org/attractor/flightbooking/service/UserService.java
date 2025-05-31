package org.attractor.flightbooking.service;

import org.attractor.flightbooking.dto.CompanyCreationDto;
import org.attractor.flightbooking.dto.CompanyDto;
import org.attractor.flightbooking.dto.UserDto;
import org.attractor.flightbooking.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    void registerUser(UserDto userDto);

    User findByEmail(String email);

    UserDto findUserDtoByEmail(String email);


    void saveAvatar(String email, String fileName);

    @Transactional
    void createCompany(CompanyCreationDto companyDto);

    @Transactional
    void toggleFreezeCompany(Long companyId);

    UserDto findUserDtoById(Long id);



    Page<UserDto> findAllCompanies(int page, int size);

    Page<CompanyDto> findAllCompaniesWithFlights(Pageable pageable);
    boolean existsByEmail(String email);
}