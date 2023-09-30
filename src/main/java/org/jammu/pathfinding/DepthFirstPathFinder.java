package org.jammu.pathfinding;

import org.jammu.city.City;
import org.jammu.city.Connection;

import java.util.*;

public class DepthFirstPathFinder extends AbstractPathFinder {

    public DepthFirstPathFinder(List<City> cityList, List<Connection> connectionList) {
        super(cityList, connectionList);
    }

    @Override

    public List<City> findPath(City startCity, City endCity) {
        //I used chatgpt here. My understanding of the parent-map is that it builds the path for us.
        //It links the city to next city in line
        Map<City, City> parentMap = new HashMap<>();
        // The use of a deque is cuz in java docs they use dont use stack and instead use Deque
        Deque<City> stack = new ArrayDeque<>();
        // Reason for using a set instead of a list based (linked list or arraylist) is for the O(1) contains operation
        //This makes the code faster.
        Set<City> visitedCities = new HashSet<>();

        stack.addFirst(startCity);
        visitedCities.add(startCity);

        //Go while the stack isn't empty in which case there is no solution
        while(!stack.isEmpty()) {
            City currentCity = stack.removeFirst();

            //this means we have found a viable path
            if(currentCity == endCity) {
                return PathFinderUtils.constructPath(startCity, endCity, parentMap);
            }

            //Start adding cities to the top of the stack. Due to stack being LIFO, this will go down one "branch"
            for(City adjacentCity : adjacencyMap.get(currentCity)) {
                if(!visitedCities.contains(adjacentCity)) {
                    stack.addFirst(adjacentCity);
                    visitedCities.add(adjacentCity);
                    parentMap.put(adjacentCity, currentCity);
                }
            }
        }
        //return an empty list if there is no path.
        return Collections.emptyList();
    }
}
