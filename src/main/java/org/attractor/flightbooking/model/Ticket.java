    package org.attractor.flightbooking.model;

    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;

    @Getter
    @Setter
    @Entity
    public class Ticket {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String seatNumber;

        private String ticketClass;

        private Double price;

        private boolean isBooked;

        @ManyToOne
        @JoinColumn(name = "flight_id")
        private Flight flight;

        @OneToOne(mappedBy = "ticket")
        private Booking booking;
    }