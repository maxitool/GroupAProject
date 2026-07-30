package org.example;

import org.example.console.readers.IntConsoleReader;
import org.example.console.readers.responses.IntResponse;
import org.example.console.readers.responses.StringResponse;
import org.example.console.readers.StringConsoleReader;
import org.example.collections.CustomArrayList;
import org.example.models.car.Car;
import org.example.models.car.CarDeserializer;
import org.example.models.car.CarValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Year;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CarFiller {
    private static final List<String> CARS_BRANDS = List.of(
            "Audi", "BMW", "Mercedes-Benz", "Porsche", "Volkswagen", "Opel",
            "Alfa Romeo", "Ferrari", "Lamborghini", "Maserati",
            "Citroen", "Peugeot", "Renault", "Bugatti",
            "Aston Martin", "Bentley", "Jaguar", "Lotus", "MG", "Rolls-Royce",
            "Toyota", "Honda", "Nissan", "Mazda", "Suzuki", "Mitsubishi", "Subaru", "Daihatsu", "Lexus", "Infiniti",
            "Ford", "Chevrolet", "Cadillac", "Buick", "Chrysler", "Jeep", "Dodge", "Tesla"
    );
    private static final int MAX_MODEL_LENGTH = 8;
    private static final int MAX_HORSEPOWER_VALUE = 500;
    private static final int MIN_HORSEPOWER_VALUE = 50;
    private static final int MIN_YEAR_VALUE = 1894;

    public static CustomArrayList<Car> fillFromConsole(){
        System.out.println("Enter number of cars: ");
        IntResponse countResponse;
        do {
            countResponse = IntConsoleReader.getIntData();
        } while (countResponse.state != StringResponse.States.BACK_COMMAND && countResponse.state != StringResponse.States.OK);
        if (countResponse.state == StringResponse.States.BACK_COMMAND) return new CustomArrayList<>();;
        int count = countResponse.intData;
        CustomArrayList<Car> cars = new CustomArrayList<>();
        System.out.println("Enter car data in format: horsepower=123, model='BMW X5', year=2020");
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
                    if (input == null || input.trim().isEmpty()){
                        System.out.println("Empty input, skipping car #" + (i + 1));
                        return;
                    }
                    Car car = CarDeserializer.stringToCar(input);
                    if (CarValidator.validateCar(car)) {
                        cars.add(car);
                        System.out.println("Car #" + (i + 1) + " added successfully!");
                    }else{
                        System.out.println("Invalid car data, skipping car #" + (i + 1));
                        System.out.println("Expected format: horsepower=123, model='BMW X5', year=2020");
                    }
                });
        System.out.println("Successfully added " + cars.size() + " valid cars.");
        return cars;
    }

    public static CustomArrayList<Car> fillFromFile(String filePath){
        CustomArrayList<Car> cars = new CustomArrayList<>();
        try(Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.map(CarDeserializer::stringToCar)
                    .filter(CarValidator::validateCar)
                    .forEach(cars::add);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return cars;
    }

    public static CustomArrayList<Car> fillRandom(int count){
        Random random = new Random();
        CustomArrayList<Car> cars = new CustomArrayList<>();
        Stream.generate(() -> {
            StringBuilder brandAndModel = new StringBuilder(CARS_BRANDS.get(random.nextInt(CARS_BRANDS.size())) + ' ');
            int modelLength = random.nextInt(MAX_MODEL_LENGTH) + 1;
            for (int j = 0; j < modelLength; j++) {
                brandAndModel.append((char) (random.nextInt(26) + 65));
            }
            return Car.builder().
                    horsepower(random.nextInt(MAX_HORSEPOWER_VALUE - MIN_HORSEPOWER_VALUE + 1) + MIN_HORSEPOWER_VALUE).
                    model(brandAndModel.toString()).
                    year(random.nextInt(Year.now().getValue() - MIN_YEAR_VALUE) + MIN_YEAR_VALUE).
                    build();
        })
                .filter(CarValidator::validateCar)
                .limit(count)
                .forEach(cars::add);
        return cars;
    }




}
