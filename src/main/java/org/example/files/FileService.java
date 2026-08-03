package org.example.files;

import org.example.collections.CustomArrayList;
import org.example.models.car.Car;

import java.io.File;
import java.util.AbstractList;
import java.util.List;
import java.util.function.Supplier;

public class FileService {

    public static boolean isFileExist(String filename) {
        if (filename == null) {
            return false;
        }
        File file = new File(filename);
        return file.exists();
    }

    public static List<Car> readCars(FileServiceStrategy strategy, String filename, Supplier<AbstractList<Car>> createCollectionSupplier) {
        if (strategy == null) {
            System.out.println("Strategy in FileService.readCars is null.");
            return new CustomArrayList<>();
        }
        return strategy.readCars(filename, createCollectionSupplier);
    }
    public static List<Car> readCars(FileServiceStrategy strategy, String filename) {
        return readCars(strategy, filename, CustomArrayList::new);
    }

    public static boolean writeCars(FileServiceStrategy strategy, String filename, boolean isAppend, List<Car> cars) {
        if (strategy == null) {
            System.out.println("Strategy in FileService.writeCars is null.");
            return false;
        }
        return strategy.writeCars(filename, isAppend, cars);
    }
}
