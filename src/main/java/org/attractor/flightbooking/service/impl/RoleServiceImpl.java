package org.attractor.flightbooking.service.impl;

import lombok.RequiredArgsConstructor;
import org.attractor.flightbooking.exception.ResourceNotFoundException;
import org.attractor.flightbooking.model.Role;
import org.attractor.flightbooking.repository.RoleRepository;
import org.attractor.flightbooking.service.RoleService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Override
    public Role findRoleByName(String name) {
        return repository.findByRoleName(name).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }
}
