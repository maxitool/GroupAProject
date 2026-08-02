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
    void isFileExist_existingFile_returnsTrue() throws Exception {
        Path file = tempDirectory.resolve("cars.txt");
        Files.createFile(file);

        boolean result = FileService.isFileExist(file.toString());

        assertTrue(result);
    }

    @Test
    void isFileExist_nonExistingFile_returnsFalse() {
        Path file = tempDirectory.resolve("non-existing.txt");

        boolean result = FileService.isFileExist(file.toString());

        assertFalse(result);
    }

    @Test
    void isFileExist_nullFilename_returnsFalse() {
        boolean result = FileService.isFileExist(null);

        assertFalse(result);
    }

    @Test
    void readCars_nullStrategy_returnsEmptyCollection() {
        List<Car> cars = FileService.readCars(
                null,
                "cars.txt"
        );

        assertNotNull(cars);
        assertTrue(cars.isEmpty());
    }

    @Test
    void writeCars_nullStrategy_returnsFalse() {
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
    void writeAndReadCars_txtStrategy_returnsOriginalCars() {
        Path file = tempDirectory.resolve("cars.txt");
        TxtFileService strategy = new TxtFileService();

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
