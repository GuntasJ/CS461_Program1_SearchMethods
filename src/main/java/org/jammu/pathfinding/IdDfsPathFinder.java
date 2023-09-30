package org.jammu.pathfinding;

import org.jammu.city.City;
import org.jammu.city.Connection;

import java.util.*;

public class IdDfsPathFinder extends AbstractPathFinder {

    public IdDfsPathFinder(List<City> cityList, List<Connection> connectionList) {
        super(cityList, connectionList);
    }

    //This algo will be similar to DFS with some key differences
    private List<City> searchWithLimitedDepth(City startCity, City endCity, int maxDepth) {
        Map<City, City> parentMap = new HashMap<>();
        Deque<City> stack = new ArrayDeque<>();
        Set<City> visitedCities = new HashSet<>();

        stack.addFirst(startCity);
        visitedCities.add(startCity);

        while(!stack.isEmpty()) {
            City currentCity = stack.removeFirst();
            if(currentCity == endCity) {
                return PathFinderUtils.constructPath(startCity, endCity, parentMap);
            }

            //First key difference
            //Here we only proceed with the pushing of the cities on to the stack IF
            //the amount of cities that have a connection to the current city are less than the max depth
            //This restricts when we can proceed with pushing the cities on the stack
            //If a city has many connections, then we will wait until the max depth increases.
            if(parentMap.keySet().stream()
                    .filter(city -> parentMap.get(city) == currentCity).count() < maxDepth) {
                for(City adjacentCity : adjacencyMap.get(currentCity)) {
                    if(!visitedCities.contains(adjacentCity)) {
                        stack.push(adjacentCity);
                        visitedCities.add(adjacentCity);
                        parentMap.put(adjacentCity, currentCity);
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<City> findPath(City startCity, City endCity) {
        //This loop is used to set the maxDepth for the dfs.
        //Will essentially do many dfs search to find the path
        for(int maxDepth = 0; maxDepth < Integer.MAX_VALUE; maxDepth++) {
            List<City> path = searchWithLimitedDepth(startCity, endCity, maxDepth);
            if(!path.isEmpty()) {
                return path;
            }
        }
        return Collections.emptyList();
    }

}
