package org.jammu.pathfinding;

import org.jammu.city.City;
import org.jammu.city.Connection;

import java.util.*;

public class BestFirstSearchPathFinder extends AbstractPathFinder {

    public BestFirstSearchPathFinder(List<City> cityList, List<Connection> connectionList) {
        super(cityList, connectionList);
    }

    @Override
    public List<City> findShortestPath(City startCity, City endCity) {
        PriorityQueue<City> priorityQueue = new PriorityQueue<>((city1, city2) -> {
            double distance1 = PathFinderUtils.calculateDistance(city1, endCity);
            double distance2 = PathFinderUtils.calculateDistance(city2, endCity);
            return Double.compare(distance1, distance2);
        });

        Map<City, City> parentMap = new HashMap<>();
        Set<City> visited = new HashSet<>();

        priorityQueue.offer(startCity);
        visited.add(startCity);

        while (!priorityQueue.isEmpty()) {
            City currentCity = priorityQueue.poll();

            if (currentCity.equals(endCity)) {
                return PathFinderUtils.reconstructPath(parentMap, startCity, endCity);
            }

            for (City neighbor : adjacencyMap.get(currentCity)) {
                if (!visited.contains(neighbor)) {
                    priorityQueue.offer(neighbor);
                    visited.add(neighbor);
                    parentMap.put(neighbor, currentCity);
                }
            }
        }

        // If no path is found
        return new ArrayList<>();
    }
}
