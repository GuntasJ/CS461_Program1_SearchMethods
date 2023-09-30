package org.jammu.pathfinding;

import org.jammu.city.City;
import org.jammu.city.Connection;

import java.util.*;

public class AStarPathFinder extends AbstractPathFinder {

    public AStarPathFinder(List<City> cityList, List<Connection> connectionList) {
        super(cityList, connectionList);
    }

    @Override
    public List<City> findPath(City startCity, City endCity) {
        Map<City, City> parentMap = new HashMap<>();
        //cost from start to n
        Map<City, Double> gScoreMap = new HashMap<>();
        //cost from start to goal
        //h is cost of n to goal
        Map<City, Double> fScoreMap = new HashMap<>();
        Queue<City> openSet = new PriorityQueue<>(Comparator.comparingDouble(fScoreMap::get));
        Set<City> closedSet = new HashSet<>();

        //start off with starting cost being 0 for g
        gScoreMap.put(startCity, 0.0);
        //start off with starting cost being the distance between start and end city for f
        //This is a direct single distance.
        fScoreMap.put(startCity, PathFinderUtils.calculateDistance(startCity, endCity));
        openSet.add(startCity);

        while(!openSet.isEmpty()) {
            City currentCity = openSet.remove();

            if(currentCity == endCity) {
                return PathFinderUtils.constructPath(startCity, endCity, parentMap);
            }

            //Unlike other algorithms, here we are seeing adding the current city to the closed set after our
            //found path check
            closedSet.add(currentCity);

            for(City adjacentCity : adjacencyMap.get(currentCity)) {
                //no point in continuing if the next city we are checking is already in the closed set
                if(closedSet.contains(adjacentCity)) {
                    continue;
                }
                //calculate a possible g score to the next city
                double tentativeGScore = gScoreMap.get(currentCity) +
                        PathFinderUtils.calculateDistance(currentCity, adjacentCity);

                //two conditions:
                //if our open set does not have the adjacent city
                //if our g score we have calculated is less than the g score to the adjacent city

                //That second check is to ensure that even if we already have this adjacent city in the open set,
                //if our path from current to adjacent is a better path than what from start to adjacent
                //to choose our path.
                //Because we are using straight distance, and a straight line is the quickest way to get from
                //point a to b, this second condition should not apply.
                if(!openSet.contains(adjacentCity) || tentativeGScore < gScoreMap.get(adjacentCity)) {
                    //calculate all the scores
                    parentMap.put(adjacentCity, currentCity);
                    gScoreMap.put(adjacentCity, tentativeGScore);
                    fScoreMap.put(adjacentCity,
                            gScoreMap.get(adjacentCity) + PathFinderUtils.calculateDistance(adjacentCity, endCity));
                    //This should always happen, as second condition should usually not be true.
                    if(!openSet.contains(adjacentCity)) {
                        openSet.add(adjacentCity);
                    }
                }
            }

        }
        return Collections.emptyList();
    }
}
