package org.example.files;

import org.example.collections.CustomArrayList;
import org.example.models.car.Car;
import org.example.models.car.CarDeserializer;
import org.example.models.car.CarValidator;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TxtFileServiceStrategy implements FileServiceStrategy {
    private static final String FILE_FORMAT = ".txt";

    @Override
    public String getFileFormat() {
        return FILE_FORMAT;
    }

    @Override
    public boolean isFileFormatGood(String filename) {
        if (filename == null) {
            return false;
        }
        return filename.toLowerCase().endsWith(FILE_FORMAT);
    }

    @Override
    public List<Car> readCars(String filename, Supplier<AbstractList<Car>> createCollectionSupplier) {
        if (filename == null) {
            System.out.println("filename = null");
            return createCollectionSupplier.get();
        }
        if (!isFileFormatGood(filename)) {
            System.out.println("The filename format is must be " + FILE_FORMAT);
            return createCollectionSupplier.get();
        }
        if (!FileService.isFileExist(filename)) {
            System.out.println("The file isn't exist");
            return createCollectionSupplier.get();
        }
        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            return lines
                    .map(CarDeserializer::stringToCar)
                    .filter(CarValidator::validateCar)
                    .collect(Collectors.toCollection(createCollectionSupplier));
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return createCollectionSupplier.get();
    }
    @Override
    public List<Car> readCars(String filename) {
        return readCars(filename, CustomArrayList::new);
    }

    @Override
    public boolean writeCars(String filename, boolean isAppend, List<Car> cars) {
        if (filename == null) {
            System.out.println("filename = null.");
            return false;
        }
        if (cars == null || cars.isEmpty()) {
            System.out.println("No data to write!");
            return false;
        }
        if (!isFileFormatGood(filename)) {
            System.out.println("The filename format is must be " + FILE_FORMAT);
            return false;
        }
        try (FileWriter writer = new FileWriter(filename, isAppend)) {
            for (Car car : cars) {
                writer.write(car.toString() + "\n");
            }
            System.out.println("Data saved to " + filename);
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        return false;
    }
}