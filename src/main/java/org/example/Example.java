package org.example;

import org.example.ConsoleReaders.BooleanConsoleReader;
import org.example.ConsoleReaders.DoubleConsoleReader;
import org.example.ConsoleReaders.IntConsoleReader;
import org.example.ConsoleReaders.StringConsoleReader;
import org.example.ConsoleReaders.Responses.BooleanResponse;
import org.example.ConsoleReaders.Responses.DoubleResponse;
import org.example.ConsoleReaders.Responses.IntResponse;
import org.example.ConsoleReaders.Responses.StringResponse;

import java.util.*;

public class Example {
    private static final List<Car> BMW_CARS_LIST_EXAMPLE = List.of(
            new Car(375, "BMW 7 Series 740i", (short)2026),
            new Car(240, "BMW X1 xDrive28i", (short)2014),
            new Car(225, "BMW X3 3.0i", (short)2006),
            new Car(255, "BMW 4 Series Gran Coupe", (short)2026),
            new Car(228, "BMW X1 xDrive28i", (short)2016),
            new Car(555, "BMW X5 M Base", (short)2013),
            new Car(335, "BMW 8 Series Gran Coupe", (short)2025)
            );

    public static List<Car> getBMWCarsList() { return List.copyOf(BMW_CARS_LIST_EXAMPLE); }
    public static LinkedList<Car> getBMWCarsLinkedList() { return new LinkedList<>(BMW_CARS_LIST_EXAMPLE); }
    public static Set<Car> getBMWCarsHashSet() { return new HashSet<>(BMW_CARS_LIST_EXAMPLE); }

    public static void exampleConsolePrintOneCar() { System.out.println(getBMWCarsList().get(0).toString());}

    public static Car exampleStringToCar() {
        List<Car> cars = getBMWCarsList();
        String carString = cars.get(0).toString();
        Car car = CarDeserializer.stringToCar(carString);
        return car;
    }

    public static void readDataFromConsole() {
        String stringResult = "";
        StringResponse stringResponse = StringConsoleReader.getStringData();
        if (stringResponse.state == StringResponse.States.OK) {
            stringResult = stringResponse.stringData;
        }

        int intResult = 0;
        IntResponse intResponse = IntConsoleReader.getIntData();
        if (intResponse.state == StringResponse.States.OK) {
            intResult = intResponse.intData;
        }

        double doubleResult = 0;
        DoubleResponse doubleResponse = DoubleConsoleReader.getDoubleData();
        if (doubleResponse.state == StringResponse.States.OK) {
            doubleResult = doubleResponse.doubleData;
        }

        boolean booleanResult = false;
        BooleanResponse booleanResponse = BooleanConsoleReader.getBooleanData();
        if (booleanResponse.state == StringResponse.States.OK) {
            booleanResult = booleanResponse.booleanData;
        }

        // BACK_COMMAND нужна для возвращения к предыдущему шагу программы
        // BACK_COMMAND state есть во всех классах Response, перечисленных выше
        if (stringResponse.state == StringResponse.States.BACK_COMMAND) {
            // go back
            return;
        }
    }
}
