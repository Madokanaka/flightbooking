package org.attractor.flightbooking.repository;

import org.attractor.flightbooking.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("SELECT f FROM Flight f WHERE " +
            "(:departureCity IS NULL OR f.departureCity = :departureCity) AND " +
            "(:arrivalCity IS NULL OR f.arrivalCity = :arrivalCity) AND " +
            "f.departureTime >= :departureDate AND " +
            "f.arrivalTime <= :returnDate")
    Page<Flight> findByFilters(
            @Param("departureCity") String departureCity,
            @Param("arrivalCity") String arrivalCity,
            @Param("departureDate") LocalDateTime departureDate,
            @Param("returnDate") LocalDateTime returnDate,
            Pageable pageable);

    Page<Flight> findByDepartureCityAndArrivalCity(String departureCity, String arrivalCity, Pageable pageable);

}