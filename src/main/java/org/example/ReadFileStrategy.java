package org.example;

import org.example.collections.CustomArrayList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class ReadFileStrategy implements FileOperationStrategy{
    @Override
    public void execute(String filename, CustomArrayList<Car> cars) {
        try {
            CustomArrayList<Car> loadedCars = Files.lines(Paths.get(filename))
                    .filter(line -> !line.trim().isEmpty())
                    .map(CarDeserializer::stringToCar)
                    .filter(car -> car != null && CarValidator.validateCar(car))
                    .collect(Collectors.toCollection(CustomArrayList::new));

            cars.clear();
            cars.addAll(loadedCars);


            System.out.println("Read " + cars.size() + " cars from " + filename);

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }


}
