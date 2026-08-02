package org.example.files;

import org.example.collections.CustomArrayList;
import org.example.models.car.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TxtFileServiceStrategyTest {

    @TempDir
    Path tempDirectory;

    private final TxtFileServiceStrategy txtFileService = new TxtFileServiceStrategy();

    @Test
    void when_getFileFormatOfTxtFileService_then_returnTxtFormat() {
        assertEquals(".txt", txtFileService.getFileFormat());
    }

    @Test
    void when_isFileFormatGoodWithtxtFilenames_then_returnTrue() {
        assertTrue(txtFileService.isFileFormatGood("cars.txt"));
        assertTrue(txtFileService.isFileFormatGood("CARS.TXT"));
    }

    @Test
    void when_isFileFormatGoodWithWrongFormat_then_returnFalse() {
        assertFalse(txtFileService.isFileFormatGood("cars.csv"));
        assertFalse(txtFileService.isFileFormatGood("cars.json"));
        assertFalse(txtFileService.isFileFormatGood(null));
    }

    @Test
    void when_writeCarsWithOverwriteMode_then_returnTrueAndWritesCarsToFile() throws IOException {
        Path file = tempDirectory.resolve("cars.txt");

        CustomArrayList<Car> cars = new CustomArrayList<>();
        cars.add(new Car(150, "Toyota", 2020));
        cars.add(new Car(250, "BMW", 2021));

        boolean result = txtFileService.writeCars(
                file.toString(),
                false,
                cars
        );

        assertTrue(result);
        assertTrue(Files.exists(file));

        List<String> lines = Files.readAllLines(file);

        assertEquals(2, lines.size());
        assertTrue(lines.get(0).contains("Toyota"));
        assertTrue(lines.get(1).contains("BMW"));
    }

    @Test
    void when_writeCarsWithOverwriteMode_then_returnTrueWithReplacesExistingData() throws IOException {
        Path file = tempDirectory.resolve("cars.txt");

        CustomArrayList<Car> oldCars = new CustomArrayList<>();
        oldCars.add(new Car(100, "Old car", 2010));

        assertTrue(txtFileService.writeCars(
                file.toString(),
                false,
                oldCars
        ));

        CustomArrayList<Car> newCars = new CustomArrayList<>();
        newCars.add(new Car(300, "New car", 2024));

        boolean result = txtFileService.writeCars(
                file.toString(),
                false,
                newCars
        );

        assertTrue(result);

        List<String> lines = Files.readAllLines(file);

        assertEquals(1, lines.size());
        assertTrue(lines.get(0).contains("New car"));
        assertFalse(lines.get(0).contains("Old car"));
    }

    @Test
    void when_writeCarsWithAppendMode_then_returnTrueWithAddsCarsToExistingFile() throws IOException {
        Path file = tempDirectory.resolve("cars.txt");

        CustomArrayList<Car> firstCars = new CustomArrayList<>();
        firstCars.add(new Car(100, "Toyota", 2010));

        assertTrue(txtFileService.writeCars(
                file.toString(),
                false,
                firstCars
        ));

        CustomArrayList<Car> additionalCars = new CustomArrayList<>();
        additionalCars.add(new Car(200, "Honda", 2015));
        additionalCars.add(new Car(300, "BMW", 2020));

        boolean result = txtFileService.writeCars(
                file.toString(),
                true,
                additionalCars
        );

        assertTrue(result);

        List<String> lines = Files.readAllLines(file);

        assertEquals(3, lines.size());
        assertTrue(lines.get(0).contains("Toyota"));
        assertTrue(lines.get(1).contains("Honda"));
        assertTrue(lines.get(2).contains("BMW"));
    }

    @Test
    void when_readCarsWithValidFile_then_returnCarsFromFile() throws IOException {
        Path file = tempDirectory.resolve("cars.txt");

        Files.writeString(
                file,
                "Car{horsepower=150, model='Toyota', year=2020}\n" +
                        "Car{horsepower=250, model='BMW', year=2021}\n"
        );

        List<Car> cars = txtFileService.readCars(file.toString());

        assertEquals(2, cars.size());

        assertEquals(150, cars.get(0).getHorsepower());
        assertEquals("Toyota", cars.get(0).getModel());
        assertEquals(2020, cars.get(0).getYear());

        assertEquals(250, cars.get(1).getHorsepower());
        assertEquals("BMW", cars.get(1).getModel());
        assertEquals(2021, cars.get(1).getYear());
    }

    @Test
    void when_readCarsWithInvalidCarInFile_then_returnCarsFromFileAndSkipsInvalidCar() throws IOException {
        Path file = tempDirectory.resolve("cars.txt");

        Files.writeString(
                file,
                "Car{horsepower=150, model='Toyota', year=2020}\n" +
                        "Car{horsepower=-100, model='Invalid', year=2020}\n" +
                        "Car{horsepower=250, model='BMW', year=2021}\n"
        );

        List<Car> cars = txtFileService.readCars(file.toString());

        assertEquals(2, cars.size());
        assertEquals("Toyota", cars.get(0).getModel());
        assertEquals("BMW", cars.get(1).getModel());
    }

    @Test
    void when_readCarsWithNonExistingFile_then_returnEmptyCollection() {
        Path file = tempDirectory.resolve("non-existing.txt");

        List<Car> cars = txtFileService.readCars(file.toString());

        assertNotNull(cars);
        assertTrue(cars.isEmpty());
    }

    @Test
    void when_readCarsWithWrongFileFormat_then_returnEmptyCollection() {
        Path file = tempDirectory.resolve("cars.csv");

        List<Car> cars = txtFileService.readCars(file.toString());

        assertNotNull(cars);
        assertTrue(cars.isEmpty());
    }

    @Test
    void when_writeCarsWithEmptyCollection_then_returnFalse() {
        Path file = tempDirectory.resolve("cars.txt");
        CustomArrayList<Car> cars = new CustomArrayList<>();

        boolean result = txtFileService.writeCars(
                file.toString(),
                false,
                cars
        );

        assertFalse(result);
        assertFalse(Files.exists(file));
    }

    @Test
    void when_writeCarsWithNullCollection_then_returnFalse() {
        Path file = tempDirectory.resolve("cars.txt");

        boolean result = txtFileService.writeCars(
                file.toString(),
                false,
                null
        );

        assertFalse(result);
        assertFalse(Files.exists(file));
    }

    @Test
    void when_writeCarsWithWrongFileFormat_then_returnFalse() {
        Path file = tempDirectory.resolve("cars.csv");

        CustomArrayList<Car> cars = new CustomArrayList<>();
        cars.add(new Car(150, "Toyota", 2020));

        boolean result = txtFileService.writeCars(
                file.toString(),
                false,
                cars
        );

        assertFalse(result);
        assertFalse(Files.exists(file));
    }

    @Test
    void when_writeAndReadCars_then_returnPreservesOriginalCars() {
        Path file = tempDirectory.resolve("cars.txt");

        CustomArrayList<Car> originalCars = new CustomArrayList<>();
        originalCars.add(new Car(180, "Audi A4", 2018));
        originalCars.add(new Car(350, "Mercedes C", 2022));

        assertTrue(txtFileService.writeCars(
                file.toString(),
                false,
                originalCars
        ));

        List<Car> restoredCars =
                txtFileService.readCars(file.toString());

        assertEquals(originalCars.size(), restoredCars.size());
        assertEquals(originalCars.get(0), restoredCars.get(0));
        assertEquals(originalCars.get(1), restoredCars.get(1));
    }
}
