package org.jammu.city;

public record City(String name, double latitude, double longitude) {
    @Override
    public String toString() {
        return name + " -> ";
    }
}
