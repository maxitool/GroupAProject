package org.example;

import org.example.collections.CustomArrayList;
import org.example.models.car.Car;

public interface FileOperationStrategy {
    void execute(String filename, CustomArrayList<Car> cars);
}

