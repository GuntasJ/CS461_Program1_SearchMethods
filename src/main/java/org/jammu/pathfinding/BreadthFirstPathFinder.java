package org.jammu.pathfinding;

import org.jammu.city.City;
import org.jammu.city.Connection;

import java.util.*;

public class BreadthFirstPathFinder extends AbstractPathFinder {
    public BreadthFirstPathFinder(List<City> cityList, List<Connection> connectionList) {
        super(cityList, connectionList);
    }

    @Override
    public List<City> findShortestPath(City startCity, City endCity) {
        Queue<City> queue = new LinkedList<>();
        Map<City, City> parentMap = new HashMap<>();
        Set<City> visited = new HashSet<>();

        queue.add(startCity);
        visited.add(startCity);

        while (!queue.isEmpty()) {
            City currentCity = queue.poll();

            if (currentCity.equals(endCity)) {
                return PathFinderUtils.reconstructPath(parentMap, startCity, endCity);
            }

            for (City neighbor : adjacencyMap.get(currentCity)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, currentCity);
                }
            }
        }
        return new ArrayList<>();
    }


}
