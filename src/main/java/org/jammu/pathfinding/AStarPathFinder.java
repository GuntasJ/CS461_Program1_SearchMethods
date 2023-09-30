package org.jammu.pathfinding;

import org.jammu.city.City;
import org.jammu.city.Connection;

import java.util.*;

public class AStarPathFinder extends AbstractPathFinder {

    public AStarPathFinder(List<City> cityList, List<Connection> connectionList) {
        super(cityList, connectionList);
    }

    private List<City> reconstructPath(Map<City, City> cameFrom, City currentCity) {
        List<City> path = new ArrayList<>();
        while (cameFrom.containsKey(currentCity)) {
            path.add(currentCity);
            currentCity = cameFrom.get(currentCity);
        }
        path.add(currentCity);
        Collections.reverse(path);
        return path;
    }


    @Override
    public List<City> findShortestPath(City startCity, City endCity) {
        PriorityQueue<City> openSet = new PriorityQueue<>((city1, city2) -> {
            double f1 = PathFinderUtils.calculateDistance(city1, endCity) +
                    PathFinderUtils.calculateDistance(startCity, city1);
            double f2 = PathFinderUtils.calculateDistance(city2, endCity) +
                    PathFinderUtils.calculateDistance(startCity, city2);
            return Double.compare(f1, f2);
        });

        Map<City, City> cameFrom = new HashMap<>();
        Map<City, Double> gScore = new HashMap<>();
        openSet.offer(startCity);
        gScore.put(startCity, 0.0);

        while (!openSet.isEmpty()) {
            City currentCity = openSet.poll();

            if (currentCity.equals(endCity)) {
                return reconstructPath(cameFrom, currentCity);
            }

            for (City neighbor : adjacencyMap.get(currentCity)) {
                double tentativeGScore = gScore.getOrDefault(currentCity, Double.MAX_VALUE)
                        + PathFinderUtils.calculateDistance(currentCity, neighbor);

                if (tentativeGScore < gScore.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    cameFrom.put(neighbor, currentCity);
                    gScore.put(neighbor, tentativeGScore);
                    double fScore = tentativeGScore + PathFinderUtils.calculateDistance(neighbor, endCity);
                    openSet.offer(neighbor);
                }
            }
        }

        // If no path is found
        return new ArrayList<>();
    }
}
