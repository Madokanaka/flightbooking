package org.attractor.flightbooking.repository;

import org.attractor.flightbooking.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
    boolean existsByEmail(String email);
    List<User> findByRolesRoleName(String roleName);
    Page<User> findByRolesRoleName(String roleName, Pageable pageable);


}
