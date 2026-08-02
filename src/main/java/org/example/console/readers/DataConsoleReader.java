package org.example.console.readers;

import org.example.collections.CustomArrayList;
import org.example.console.readers.primitives.IntConsoleReader;
import org.example.console.readers.primitives.StringConsoleReader;
import org.example.console.readers.primitives.responses.IntResponse;
import org.example.console.readers.primitives.responses.StringResponse;
import org.example.models.car.Car;
import org.example.models.car.CarDeserializer;
import org.example.models.car.CarValidator;

import java.util.AbstractList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class DataConsoleReader {

    public static List<Car> readCars(Supplier<AbstractList<Car>> createCollectionSupplier) {
        List<Car> cars = createCollectionSupplier.get();
        System.out.println("Enter number of cars: ");
        IntResponse countResponse;
        do {
            countResponse = IntConsoleReader.getIntData();
        } while (countResponse.state != StringResponse.States.BACK_COMMAND && countResponse.state != StringResponse.States.OK);
        if (countResponse.state == StringResponse.States.BACK_COMMAND) {
            return cars;
        }
        if (countResponse.intData < 0) {
            System.out.println("The wrote number must be greater then or equal to 0");
            return cars;
        }
        int count = countResponse.intData;
        System.out.println("Enter car data in format: " + Car.CAR_FORMAT);
        System.out.println("Or type 'back' to return to the previous menu.");
        java.util.concurrent.atomic.AtomicBoolean isBack = new java.util.concurrent.atomic.AtomicBoolean(false);
        IntStream.range(0,count)
                .takeWhile(i -> !isBack.get())
                .forEach(i -> {
                    System.out.print("Car #" + (i + 1) + " > ");
                    StringResponse inputResponse;
                    do {
                        inputResponse = StringConsoleReader.getStringData();
                    } while (inputResponse.state != StringResponse.States.BACK_COMMAND && inputResponse.state != StringResponse.States.OK);
                    if (inputResponse.state == StringResponse.States.BACK_COMMAND) {
                        isBack.set(true);
                        return;
                    }
                    String input = inputResponse.stringData;
                    if (input == null || input.trim().isEmpty()) {
                        System.out.println("Empty input, skipping car #" + (i + 1));
                        return;
                    }
                    Car car = CarDeserializer.stringToCar(input);
                    if (CarValidator.validateCar(car, false)) {
                        cars.add(car);
                        System.out.println("Car #" + (i + 1) + " added successfully!");
                    } else {
                        System.out.println("Invalid car data, skipping car #" + (i + 1));
                        System.out.println("Expected format: " + Car.CAR_FORMAT);
                    }
                });
        System.out.println("Successfully added " + cars.size() + " valid cars.");
        return cars;
    }
    public static List<Car> readCars() {
        return readCars(CustomArrayList::new);
    }
}
