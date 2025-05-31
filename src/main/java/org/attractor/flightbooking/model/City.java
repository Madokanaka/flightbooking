package org.attractor.flightbooking.model;

import lombok.Getter;

@Getter
public enum City {
    NEW_YORK("New York"),
    LONDON("London"),
    PARIS("Paris"),
    TOKYO("Tokyo"),
    DUBAI("Dubai"),
    SINGAPORE("Singapore"),
    SYDNEY("Sydney"),
    LOS_ANGELES("Los Angeles"),
    MOSCOW("Moscow"),
    MUMBAI("Mumbai"),
    DUBLIN("Dublin");

    private final String displayName;

    City(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}