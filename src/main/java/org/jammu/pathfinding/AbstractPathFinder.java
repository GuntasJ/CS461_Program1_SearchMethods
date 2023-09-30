package org.jammu.pathfinding;

import org.jammu.city.City;
import org.jammu.city.Connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractPathFinder implements PathFinder {

    protected Map<City, List<City>> adjacencyMap;

    protected AbstractPathFinder(List<City> cityList, List<Connection> connectionList) {
        adjacencyMap = new HashMap<>();
        cityList.forEach(this::addCityNode);
        connectionList.forEach(this::addCityConnection);
    }

    private void addCityNode(City city) {
        adjacencyMap.put(city, new ArrayList<>());
    }

    private void addCityConnection(City city1, City city2) {
        adjacencyMap.get(city1).add(city2);
        adjacencyMap.get(city2).add(city1);
    }

    private void addCityConnection(Connection connection) {
        addCityConnection(connection.getCity1(), connection.getCity2());
    }
}
