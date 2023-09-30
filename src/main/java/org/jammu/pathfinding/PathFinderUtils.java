package org.jammu.pathfinding;

import org.jammu.city.City;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PathFinderUtils {

    //d = ((x1 - x2)^2 + (y1 - y2)^2))^(1/2)
    //This is also the formula used to calculate the heuristic used in the BestFirst and A*
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


    //Reconstructs the path from the parent map. The parent map is in reverse order so this reverses it and returns a list
    public static List<City> constructPath(City startCity, City endCity, Map<City, City> parentMap) {
        List<City> path = new ArrayList<>();
        City traceCity = endCity;
        while(traceCity != startCity) {
            path.add(traceCity);
            traceCity = parentMap.get(traceCity);
        }
        path.add(startCity);
        //O(n/2) => O(n) operation so this shouldn't affect results too much
        Collections.reverse(path);
        return path;
    }
}
