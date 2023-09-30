package org.jammu.pathfinding;

import org.jammu.city.City;
import org.jammu.city.Connection;

import java.util.*;

public class BreadthFirstPathFinder extends AbstractPathFinder {
    public BreadthFirstPathFinder(List<City> cityList, List<Connection> connectionList) {
        super(cityList, connectionList);
    }

    //this code is going to be almost identical to the one in the depth first search
    //only difference is going to be the visiting part down below

    @Override
    public List<City> findPath(City startCity, City endCity) {
        Map<City, City> parentMap = new HashMap<>();
        //Here is the main difference. The queue is a FIFO structure
        //This has ramifications down below
        Queue<City> queue = new ArrayDeque<>();
        Set<City> visitedCities = new HashSet<>();

        queue.add(startCity);
        visitedCities.add(startCity);

        while(!queue.isEmpty()) {
            City currentCity = queue.remove();
            if(currentCity == endCity) {
                return PathFinderUtils.constructPath(startCity, endCity, parentMap);
            }

            //This part is different from the breadth-first search
            //Because a queue is being used, it will retrieve the first inserted item, which will cause
            //for the algo to search across and not down.
            for(City adjacentCity : adjacencyMap.get(currentCity)) {
                if(!visitedCities.contains(adjacentCity)) {
                    queue.add(adjacentCity);
                    visitedCities.add(adjacentCity);
                    parentMap.put(adjacentCity, currentCity);
                }
            }
        }
        return Collections.emptyList();
    }
}
