package org.example;

import org.example.collections.CustomArrayList;
import org.example.models.car.Car;

public class FileServiceTxt {
    private static final FileContext context = new FileContext();
    public static CustomArrayList<Car> readCarsFromFile(String filename) {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        context.setStrategy(new ReadFileStrategy());
        context.executeOperation(filename, cars);
        return cars;
    }

    public static void saveCarsToFile(CustomArrayList<Car> cars, String filename, boolean append) {
        context.setStrategy(new WriteFileStrategy(append));
        context.executeOperation(filename, cars);
    }
}