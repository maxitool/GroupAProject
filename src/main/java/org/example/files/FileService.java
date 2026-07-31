package org.example.files;

import org.example.collections.CustomArrayList;
import org.example.models.car.Car;

import java.io.File;
import java.util.List;

public class FileService {

    public static boolean isFileExist(String filename) {
        if (filename == null) {
            return false;
        }
        File file = new File(filename);
        return file.exists();
    }

    public static List<Car> readCars(FileServiceStrategy strategy, String filename) {
        if (strategy == null) {
            System.out.println("Strategy in FileService.readCars is null.");
            return new CustomArrayList<>();
        }
        return strategy.readCars(filename);
    }

    public static boolean writeCars(FileServiceStrategy strategy, String filename, boolean isAppend, List<Car> cars) {
        if (strategy == null) {
            System.out.println("Strategy in FileService.writeCars is null.");
            return false;
        }
        return strategy.writeCars(filename, isAppend, cars);
    }
}
