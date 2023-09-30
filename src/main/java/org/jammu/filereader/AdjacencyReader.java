package org.jammu.filereader;

import org.jammu.city.City;
import org.jammu.city.Connection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class AdjacencyReader {
    private String fileName;

    public AdjacencyReader(String fileName) {
        this.fileName = fileName;
    }

    public List<Connection> readValues(List<City> cityList) {
        Path filePath = null;
        try {
            filePath = Path.of(getClass().getClassLoader().getResource(fileName).toURI());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try(Stream<String> lines = Files.lines(filePath)) {
            return lines.map(line -> {
                String[] splitArray = line.split(" ");
                String cityName1 = splitArray[0];
                String cityName2 = splitArray[1];


                List<City> connectionCities = cityList.stream()
                        .filter(city -> city.name().equals(cityName1) || city.name().equals(cityName2))
                        .toList();
                return new Connection(connectionCities.get(0), connectionCities.get(1));
            }).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
