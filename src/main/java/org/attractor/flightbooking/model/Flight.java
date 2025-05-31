package org.attractor.flightbooking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String flightNumber;

    private String departureCity;

    private String arrivalCity;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private User company;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}