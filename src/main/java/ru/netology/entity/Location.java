package ru.netology.entity;

import java.util.Objects;

public class Location {

    private final String city;

    private final Country country;

    private final String street;

    private final int building;

    public Location(String city, Country country, String street, int building) {
        this.city = city;
        this.country = country;
        this.street = street;
        this.building = building;
    }

    public String getCity() {
        return city;
    }

    public Country getCountry() {
        return country;
    }

    public String getStreet() {
        return street;
    }

    public int getBuilding() {
        return building;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != Location.class) {
            return false;
        }

        Location location = (Location) obj;
        return location.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, country, street, building);
    }
}
