package org.jammu.pathfinding;

import org.jammu.city.City;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PathFinderUtils {

    //d = ((x1 - x2)^2 + (y1 - y2)^2))^(1/2)
    public static double calculateDistance(City city1, City city2) {
        double x = city1.latitude() - city2.latitude();
        double y = city1.longitude() - city2.longitude();
        return Math.sqrt(x * x + y * y);
    }

    public static double calculateDistance(List<City> cities) {
        double totalDistance = 0;
        for(int i = 0; i < cities.size() - 1; i++) {
            totalDistance += calculateDistance(cities.get(i), cities.get(i + 1));
        }
        return totalDistance;
    }

    public static List<City> reconstructPath(Map<City, City> parentMap, City startCity, City endCity) {
        List<City> path = new ArrayList<>();
        City currentCity = endCity;

        while (currentCity != null) {
            path.add(currentCity);
            currentCity = parentMap.get(currentCity);

            if (currentCity != null && currentCity.equals(startCity)) {
                path.add(startCity);
                Collections.reverse(path);
                return path;
            }
        }

        // If no path is found
        return new ArrayList<>();
    }
}
