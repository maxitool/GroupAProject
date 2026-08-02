package org.example.files;

import org.example.collections.CustomArrayList;
import org.example.models.car.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileServiceTest {

    @TempDir
    Path tempDirectory;

    @Test
    void when_isFileExistWithExistingFile_then_returnTrue() throws Exception {
        Path file = tempDirectory.resolve("cars.txt");
        Files.createFile(file);

        boolean result = FileService.isFileExist(file.toString());

        assertTrue(result);
    }

    @Test
    void when_isFileExistWithNonExistingFile_then_returnFalse() {
        Path file = tempDirectory.resolve("non-existing.txt");

        boolean result = FileService.isFileExist(file.toString());

        assertFalse(result);
    }

    @Test
    void when_isFileExistWithNull_then_returnFalse() {
        boolean result = FileService.isFileExist(null);

        assertFalse(result);
    }

    @Test
    void when_readCarsWithNullStrategy_then_returnEmptyList() {
        List<Car> cars = FileService.readCars(
                null,
                "cars.txt"
        );

        assertNotNull(cars);
        assertTrue(cars.isEmpty());
    }

    @Test
    void when_writeCarsWithNullStrategy_then_returnFalse() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        cars.add(new Car(150, "Toyota", 2020));

        boolean result = FileService.writeCars(
                null,
                "cars.txt",
                false,
                cars
        );

        assertFalse(result);
    }

    @Test
    void when_writeAndReadCarsWithTxtStrategy_then_returnOriginalCars() {
        Path file = tempDirectory.resolve("cars.txt");
        TxtFileServiceStrategy strategy = new TxtFileServiceStrategy();

        CustomArrayList<Car> originalCars = new CustomArrayList<>();
        originalCars.add(new Car(150, "Toyota", 2020));
        originalCars.add(new Car(250, "BMW", 2021));

        boolean writeResult = FileService.writeCars(
                strategy,
                file.toString(),
                false,
                originalCars
        );

        List<Car> restoredCars = FileService.readCars(
                strategy,
                file.toString()
        );

        assertTrue(writeResult);
        assertEquals(originalCars, restoredCars);
    }
}
