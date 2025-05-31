package org.attractor.flightbooking.service;

import org.attractor.flightbooking.dto.UserDto;
import org.attractor.flightbooking.model.User;

public interface UserService {
    void registerUser(UserDto userDto);

    User findByEmail(String email);

    UserDto findUserDtoByEmail(String email);

    boolean existsByEmail(String email);
}