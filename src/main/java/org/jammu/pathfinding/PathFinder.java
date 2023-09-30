package org.jammu.pathfinding;

import org.jammu.city.City;

import java.util.List;

public interface PathFinder {
    List<City> findPath(City startCity, City endCity);
}
