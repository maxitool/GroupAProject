package org.example.sort;

import org.example.CarCounter;
import org.example.collections.CustomArrayList;
import org.example.models.car.Car;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;

public class CarCounterTest {

    @Test
    void countOccurrences_oneThread_returnsCorrectCount() {
        CustomArrayList<Car> cars = createCars();
        Car target = new Car(200, "BMW", 2020);

        CarCounter counter = new CarCounter(cars);

        long result = counter.countOccurrences(target, 1);

        assertEquals(4, result);
    }

    @Test
    void countOccurrences_multipleThreads_returnsCorrectCount() {
        CustomArrayList<Car> cars = createCars();
        Car target = new Car(200, "BMW", 2020);

        CarCounter counter = new CarCounter(cars);

        long result = counter.countOccurrences(target, 4);

        assertEquals(4, result);
    }

    @Test
    void countOccurrences_differentThreadCounts_returnSameResult() {
        CustomArrayList<Car> cars = createCars();
        Car target = new Car(200, "BMW", 2020);

        CarCounter counter = new CarCounter(cars);

        long oneThreadResult =
                counter.countOccurrences(target, 1);

        long twoThreadsResult =
                counter.countOccurrences(target, 2);

        long fourThreadsResult =
                counter.countOccurrences(target, 4);

        assertEquals(oneThreadResult, twoThreadsResult);
        assertEquals(oneThreadResult, fourThreadsResult);
        assertEquals(4, oneThreadResult);
    }

    @Test
    void countOccurrences_equalObjects_countsAllOccurrences() {
        CustomArrayList<Car> cars = new CustomArrayList<>();

        cars.add(new Car(200, "BMW", 2020));
        cars.add(new Car(200, "BMW", 2020));
        cars.add(new Car(200, "BMW", 2020));

        Car target = new Car(200, "BMW", 2020);
        CarCounter counter = new CarCounter(cars);

        long result = counter.countOccurrences(target, 3);

        assertEquals(3, result);
    }

    @Test
    void countOccurrences_targetIsAbsent_returnsZero() {
        CustomArrayList<Car> cars = createCars();
        Car target = new Car(500, "Ferrari", 2024);

        CarCounter counter = new CarCounter(cars);

        long result = counter.countOccurrences(target, 3);

        assertEquals(0, result);
    }

    @Test
    void countOccurrences_emptyCollection_returnsZero() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        Car target = new Car(200, "BMW", 2020);

        CarCounter counter = new CarCounter(cars);

        long result = counter.countOccurrences(target, 4);

        assertEquals(0, result);
    }

    @Test
    void countOccurrences_nullCollection_returnsZero() {
        Car target = new Car(200, "BMW", 2020);
        CarCounter counter = new CarCounter(null);

        long result = counter.countOccurrences(target, 4);

        assertEquals(0, result);
    }

    @Test
    void countOccurrences_zeroThreadCount_usesOneThread() {
        CustomArrayList<Car> cars = createCars();
        Car target = new Car(200, "BMW", 2020);

        CarCounter counter = new CarCounter(cars);

        long result = counter.countOccurrences(target, 0);

        assertEquals(4, result);
    }

    @Test
    void countOccurrences_negativeThreadCount_usesOneThread() {
        CustomArrayList<Car> cars = createCars();
        Car target = new Car(200, "BMW", 2020);

        CarCounter counter = new CarCounter(cars);

        long result = counter.countOccurrences(target, -5);

        assertEquals(4, result);
    }

    @Test
    void countOccurrences_moreThreadsThanCars_returnsCorrectCount() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        cars.add(new Car(200, "BMW", 2020));
        cars.add(new Car(100, "Audi", 2019));

        Car target = new Car(200, "BMW", 2020);
        CarCounter counter = new CarCounter(cars);

        long result = counter.countOccurrences(target, 100);

        assertEquals(1, result);
    }

    @Test
    void countOccurrences_singleCar_returnsOne() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        cars.add(new Car(200, "BMW", 2020));

        Car target = new Car(200, "BMW", 2020);
        CarCounter counter = new CarCounter(cars);

        long result = counter.countOccurrences(target, 4);

        assertEquals(1, result);
    }

    @Test
    void countOccurrences_largeCollection_returnsCorrectCount() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        Car target = new Car(200, "BMW", 2020);

        for (int i = 0; i < 5000; i++) {
            cars.add(new Car(200, "BMW", 2020));
        }

        for (int i = 0; i < 5000; i++) {
            cars.add(new Car(150, "Toyota", 2018));
        }

        CarCounter counter = new CarCounter(cars);

        long result = counter.countOccurrences(target, 8);

        assertEquals(5000, result);
    }

    @Test
    void printOccurrences_printsResultToConsole() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        cars.add(new Car(200, "BMW", 2020));
        cars.add(new Car(100, "Audi", 2019));
        cars.add(new Car(200, "BMW", 2020));

        Car target = new Car(200, "BMW", 2020);
        CarCounter counter = new CarCounter(cars);

        PrintStream originalOutput = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setOut(
                    new PrintStream(
                            output,
                            true,
                            StandardCharsets.UTF_8
                    )
            );

            counter.printOccurrences(target, 2);
        } finally {
            System.setOut(originalOutput);
        }

        String consoleOutput =
                output.toString(StandardCharsets.UTF_8);

        assertTrue(
                consoleOutput.contains("Result: 2 occurrences.")
        );
    }

    private CustomArrayList<Car> createCars() {
        CustomArrayList<Car> cars = new CustomArrayList<>();

        for (int i = 0; i < 4; i++) {
            cars.add(new Car(200, "BMW", 2020));
        }

        for (int i = 0; i < 3; i++) {
            cars.add(new Car(150, "Toyota", 2018));
        }

        for (int i = 0; i < 2; i++) {
            cars.add(new Car(300, "Mercedes", 2022));
        }

        return cars;
    }
}