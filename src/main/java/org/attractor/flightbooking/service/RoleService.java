package org.attractor.flightbooking.service;

import org.attractor.flightbooking.model.Role;

public interface RoleService {
    Role findRoleByName(String name);
}
