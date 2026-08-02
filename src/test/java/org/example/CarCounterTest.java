package org.example;

import org.example.collections.CustomArrayList;
import org.example.models.car.Car;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;

public class CarCounterTest {

    @Test
    void when_countOccurrencesWithOneThread_then_returnCorrectCount() {
        CustomArrayList<Car> cars = createCars();
        Car target = new Car(200, "BMW", 2020);

        CarCounter counter = new CarCounter(cars);

        long result = counter.countOccurrences(target, 1);

        assertEquals(4, result);
    }

    @Test
    void when_countOccurrencesWithMultipleThreads_then_returnCorrectCount() {
        CustomArrayList<Car> cars = createCars();
        Car target = new Car(200, "BMW", 2020);

        CarCounter counter = new CarCounter(cars);

        long result = counter.countOccurrences(target, 4);

        assertEquals(4, result);
    }

    @Test
    void when_countOccurrencesWithDifferentThreadCounts_then_returnSameResult() {
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
    void when_countOccurrencesWithEqualObjects_then_returnCountsAllOccurrences() {
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
    void when_countOccurrencesWithTargetIsAbsent_then_returnZero() {
        CustomArrayList<Car> cars = createCars();
        Car target = new Car(500, "Ferrari", 2024);

        CarCounter counter = new CarCounter(cars);

        long result = counter.countOccurrences(target, 3);

        assertEquals(0, result);
    }

    @Test
    void when_countOccurrencesWithEmptyCollection_then_returnZero() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        Car target = new Car(200, "BMW", 2020);

        CarCounter counter = new CarCounter(cars);

        long result = counter.countOccurrences(target, 4);

        assertEquals(0, result);
    }

    @Test
    void when_countOccurrencesWithNullCollection_then_returnZero() {
        Car target = new Car(200, "BMW", 2020);
        CarCounter counter = new CarCounter(null);

        long result = counter.countOccurrences(target, 4);

        assertEquals(0, result);
    }

    @Test
    void when_countOccurrencesWithZeroThreadCount_then_returnCountAllOccurrencesUsesOneThread() {
        CustomArrayList<Car> cars = createCars();
        Car target = new Car(200, "BMW", 2020);

        CarCounter counter = new CarCounter(cars);

        long result = counter.countOccurrences(target, 0);

        assertEquals(4, result);
    }

    @Test
    void when_countOccurrencesWithNegativeThreadCount_then_returnCountAllOccurrencesUsesOneThread() {
        CustomArrayList<Car> cars = createCars();
        Car target = new Car(200, "BMW", 2020);

        CarCounter counter = new CarCounter(cars);

        long result = counter.countOccurrences(target, -5);

        assertEquals(4, result);
    }

    @Test
    void when_countOccurrencesWithMoreThreadsThanCars_then_returnCorrectCount() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        cars.add(new Car(200, "BMW", 2020));
        cars.add(new Car(100, "Audi", 2019));

        Car target = new Car(200, "BMW", 2020);
        CarCounter counter = new CarCounter(cars);

        long result = counter.countOccurrences(target, 100);

        assertEquals(1, result);
    }

    @Test
    void when_countOccurrencesWithSingleCar_then_returnOne() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        cars.add(new Car(200, "BMW", 2020));

        Car target = new Car(200, "BMW", 2020);
        CarCounter counter = new CarCounter(cars);

        long result = counter.countOccurrences(target, 4);

        assertEquals(1, result);
    }

    @Test
    void when_countOccurrencesWithLargeCollection_then_returnCorrectCount() {
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
    void when_printOccurrencesWithLargeCollection_then_printsResultToConsole() {
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