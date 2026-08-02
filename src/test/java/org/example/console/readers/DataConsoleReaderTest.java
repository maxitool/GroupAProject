package org.example.console.readers;

import org.example.console.readers.primitives.StringConsoleReader;
import org.example.console.readers.primitives.StringConsoleReaderTest;
import org.example.models.car.Car;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataConsoleReaderTest {
    private final InputStream originalSystemIn = System.in;

    public static void provideInput(String data){
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @AfterEach
    public void closeScanner() {
        StringConsoleReader.closeScanner();
    }

    @AfterAll
    public static void clearSystemIn() {
        System.setIn(StringConsoleReaderTest.SYSTEM_IN_BACKUP);
    }

    @Test
    public void when_read2Cars_then_returnListWith2Cars() {
        provideInput("2\n" +
                "horsepower=150, model='Toyota', year=2020\n" +
                "horsepower=200, model='BMW', year=2021\n");
        List<Car> cars = DataConsoleReader.readCars();
        assertEquals(2, cars.size());
        assertEquals(150, cars.get(0).getHorsepower());
        assertEquals(200, cars.get(1).getHorsepower());
    }

    @Test
    public void when_read0Cars_then_returnEmptyList() {
        provideInput("0\n");
        List<Car> cars = DataConsoleReader.readCars();
        assertEquals(0, cars.size());
        assertTrue(cars.isEmpty());
    }

    @Test
    public void when_read1CarWithEmptyLine_then_returnEmptyList() {
        provideInput("1\n" +
                "\n");
        List<Car> cars = DataConsoleReader.readCars();
        assertEquals(0, cars.size(), "Car should not be added when empty input is provided");
        assertTrue(cars.isEmpty());
    }

    @Test
    public void when_readBackCommand_then_returnEmptyList() {
        provideInput("back\n");
        List<Car> cars = DataConsoleReader.readCars();
        assertEquals(0, cars.size());
        assertTrue(cars.isEmpty());
    }

    @Test
    public void when_read3CarsWith1InvalidAnd2ValidCars_then_returnListWith2ValidCars() {
        provideInput("3\n" +
                "horsepower=150, model='Toyota', year=2020\n" +
                "horsepower=-50, model='Bad', year=2020\n" +  // невалидный
                "horsepower=200, model='BMW', year=2021\n");
        List<Car> cars = DataConsoleReader.readCars();
        assertEquals(2, cars.size());
        assertEquals("Toyota", cars.get(0).getModel());
        assertEquals("BMW", cars.get(1).getModel());
    }
}