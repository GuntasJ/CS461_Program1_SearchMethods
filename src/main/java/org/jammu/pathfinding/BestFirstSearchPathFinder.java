package org.jammu.pathfinding;

import org.jammu.city.City;
import org.jammu.city.Connection;

import java.nio.file.Path;
import java.util.*;

public class BestFirstSearchPathFinder extends AbstractPathFinder {

    public BestFirstSearchPathFinder(List<City> cityList, List<Connection> connectionList) {
        super(cityList, connectionList);
    }

    @Override
    public List<City> findPath(City startCity, City endCity) {
        //Set up is similar to the breadth-first search
        //Have a priority queue to find the best next city to search
        Map<City, City> parentMap = new HashMap<>();
        Queue<City> cityPriorityQueue = new PriorityQueue<>(Comparator.comparingDouble(
                city -> PathFinderUtils.calculateDistance(city, endCity)
        ));
        Set<City> visitedCities = new HashSet<>();

        cityPriorityQueue.add(startCity);
        visitedCities.add(startCity);

        while (!cityPriorityQueue.isEmpty()) {
            City currentCity = cityPriorityQueue.remove();

            if (currentCity == endCity) {
                return PathFinderUtils.constructPath(startCity, endCity, parentMap);
            }

            //This is the same as the dfs and bfs only difference being the priority queue. PO in java is basically
            //a heap. This will retrieve the best item in O(log(n))

            for (City adjacentCity : adjacencyMap.get(currentCity)) {
                if (!visitedCities.contains(adjacentCity)) {
                    cityPriorityQueue.add(adjacentCity);
                    visitedCities.add(adjacentCity);
                    parentMap.put(adjacentCity, currentCity);
                }
            }
        }


        return Collections.emptyList();
    }

}
