package org.example.files;

import org.example.models.car.Car;

import java.util.List;

public interface FileServiceStrategy {
    String getFileFormat();
    boolean isFileFormatGood(String filename);
    List<Car> readCars(String filename);
    boolean writeCars(String filename, boolean isAppend, List<Car> cars);
}

