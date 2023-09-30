package org.jammu.pathfinding;

import org.jammu.city.City;
import org.jammu.city.Connection;

import java.util.*;

public class DepthFirstPathFinder extends AbstractPathFinder {

    public DepthFirstPathFinder(List<City> cityList, List<Connection> connectionList) {
        super(cityList, connectionList);
    }

    @Override
    public List<City> findShortestPath(City startCity, City endCity) {
        Set<City> visited = new HashSet<>();
        Map<City, City> parentMap = new HashMap<>();
        Stack<City> stack = new Stack<>();

        stack.push(startCity);
        visited.add(startCity);

        while (!stack.isEmpty()) {
            City currentCity = stack.pop();

            if (currentCity.equals(endCity)) {
                return PathFinderUtils.reconstructPath(parentMap, startCity, endCity);
            }

            for (City neighbor : adjacencyMap.get(currentCity)) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, currentCity);
                }
            }
        }

        // If no path is found
        return new ArrayList<>();
    }
}
