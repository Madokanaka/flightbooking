package org.attractor.flightbooking.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanyDto {
    private Long id;
    private String name;
    private String email;
    private boolean isFrozen;
    private List<FlightDto> flights;
    private String logoPath;
}