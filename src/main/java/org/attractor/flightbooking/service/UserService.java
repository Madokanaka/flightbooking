package org.attractor.flightbooking.service;

import org.attractor.flightbooking.dto.UserDto;

public interface UserService {
    void registerUser(UserDto userDto);
    boolean existsByEmail(String email);
}