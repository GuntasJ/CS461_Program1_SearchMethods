package org.jammu.userinterface;

import org.jammu.city.City;
import org.jammu.city.Connection;
import org.jammu.filereader.AdjacencyReader;
import org.jammu.filereader.CsvReader;
import org.jammu.pathfinding.*;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private CsvReader csvReader = new CsvReader("coordinates.csv");
    private AdjacencyReader adjacencyReader = new AdjacencyReader("Adjacencies.txt");

    List<City> cityList = csvReader.readValues();
    List<Connection> connectionList = adjacencyReader.readValues(cityList);

    public void printMainMenu() {
        System.out.println("""
                Welcome to City Finder Program
                Choose an option out of the following by selecting the number as listed
                1.) Brute force search
                2.) Best-first-search
                3.) A* search""");
    }

    public void printBruteForceMenu() {
        System.out.println("""
                Choose an option out of the following by selecting the number as listed
                1.) Depth-first search
                2.) Breadth-first search
                3.) ID-DFS search
                """);
    }

    public void printStartCitySelection() {
        System.out.println("Enter a start city");
    }

    public void printEndCitySelection() {
        System.out.println("Enter an end city");
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);


        while(true) {
            int selection = 0;
            String startCityName = "";
            String endCityName = "";
            boolean startCityFound = false;
            boolean endCityFound = false;

            do {
                printMainMenu();
                selection = Integer.parseInt(scanner.nextLine());
            } while (selection != 1 && selection != 2 && selection != 3);

            if(selection == 1) {
                do {
                    printBruteForceMenu();
                    selection = Integer.parseInt(scanner.nextLine()) + 10;
                } while (selection != 11 && selection != 12 && selection != 13);
            }

            do {
                printStartCitySelection();
                startCityName = scanner.nextLine();
                for(City city : cityList) {
                    if(city.name().equals(startCityName)) {
                        startCityFound = true;
                        break;
                    }
                }
            } while (!startCityFound);

            do {
                printEndCitySelection();
                endCityName = scanner.nextLine();
                for(City city : cityList) {
                    if(city.name().equals(endCityName)) {
                        endCityFound = true;
                        break;
                    }
                }
            } while (!endCityFound);

            PathFinder pathFinder = switch (selection) {
                case 2 -> new BestFirstSearchPathFinder(cityList, connectionList);
                case 3 -> new AStarPathFinder(cityList, connectionList);
                case 11 -> new DepthFirstPathFinder(cityList, connectionList);
                case 12 -> new BreadthFirstPathFinder(cityList, connectionList);
                case 13 -> new IdDfsPathFinder(cityList, connectionList);
                default -> throw new IllegalStateException("Unexpected value: " + selection);
            };

            String finalStartCityName = startCityName;
            City startCity = cityList.stream()
                    .filter(city -> city.name().equals(finalStartCityName))
                    .findFirst()
                    .get();

            String finalEndCityName = endCityName;
            City endCity = cityList.stream()
                    .filter(city -> city.name().equals(finalEndCityName))
                    .findFirst()
                    .get();

            long startTime = System.currentTimeMillis();
            List<City> path = pathFinder.findShortestPath(startCity, endCity);
            long endTime = System.currentTimeMillis();

            System.out.println();
            System.out.println("It took " + (endTime - startTime) + " ms\nThe path that it took was: ");
            path.forEach(System.out::print);
            System.out.println();
            System.out.println("Distance of path: " + PathFinderUtils.calculateDistance(path));
        }
    }

}
