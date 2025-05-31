package org.attractor.flightbooking.repository;

import org.attractor.flightbooking.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByFlightId(Long flightId);
    boolean existsByFlightIdAndSeatNumberAndIsBookedFalse(Long flightId, String seatNumber);
    boolean existsByFlightCompanyIdAndIsBookedTrue(Long companyId);
    long countByFlightCompanyIdAndIsBookedTrue(Long companyId);
}