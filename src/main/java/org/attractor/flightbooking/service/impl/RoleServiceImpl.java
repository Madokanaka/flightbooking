package org.attractor.flightbooking.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.flightbooking.exception.ResourceNotFoundException;
import org.attractor.flightbooking.model.Role;
import org.attractor.flightbooking.repository.RoleRepository;
import org.attractor.flightbooking.service.RoleService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Override
    public Role findRoleByName(String name) {
        log.info("Fetching role by name={}", name);
        return repository.findByRoleName(name).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }
}
