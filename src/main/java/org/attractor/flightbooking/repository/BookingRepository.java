package org.attractor.flightbooking.repository;

import org.attractor.flightbooking.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    boolean existsByTicketId(Long ticketId);
    Page<Booking> findByUserId(Long id, Pageable pageable);}