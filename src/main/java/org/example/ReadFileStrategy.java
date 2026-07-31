package org.example;

import org.example.collections.CustomArrayList;
import org.example.models.car.Car;
import org.example.models.car.CarDeserializer;
import org.example.models.car.CarValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ReadFileStrategy{

    public List<Car> readCars(String filename) {
        return List.of();
    }


    public boolean writeCars(String filename, List<Car> cars) {
        return false;
    }


    public void execute(String filename, List<Car> cars) {

    }


}
