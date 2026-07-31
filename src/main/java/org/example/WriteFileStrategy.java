package org.example;

import org.example.collections.CustomArrayList;
import org.example.models.car.Car;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteFileStrategy {
    private final boolean append;

    public WriteFileStrategy(boolean append) {
        this.append = append;
    }


    public List<Car> readCars(String filename) {
        return List.of();
    }


    public boolean writeCars(String filename, List<Car> cars) {
        return false;
    }


    public void execute(String filename, List<Car> cars) {


    }
}
