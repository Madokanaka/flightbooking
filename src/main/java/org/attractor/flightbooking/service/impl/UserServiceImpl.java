package org.attractor.flightbooking.service.impl;

import lombok.RequiredArgsConstructor;
import org.attractor.flightbooking.dto.UserDto;
import org.attractor.flightbooking.exception.UserNotFoundException;
import org.attractor.flightbooking.model.Role;
import org.attractor.flightbooking.model.User;
import org.attractor.flightbooking.repository.UserRepository;
import org.attractor.flightbooking.service.RoleService;
import org.attractor.flightbooking.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

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
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}