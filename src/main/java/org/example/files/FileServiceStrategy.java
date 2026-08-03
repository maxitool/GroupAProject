package org.example.files;

import org.example.models.car.Car;

import java.util.AbstractList;
import java.util.List;
import java.util.function.Supplier;

public interface FileServiceStrategy {
    String getFileFormat();
    boolean isFileFormatGood(String filename);
    List<Car> readCars(String filename, Supplier<AbstractList<Car>> createCollectionSupplier);
    List<Car> readCars(String filename);
    boolean writeCars(String filename, boolean isAppend, List<Car> cars);
}

