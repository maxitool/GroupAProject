package org.example;

import org.example.ConsoleReaders.IntConsoleReader;
import org.example.ConsoleReaders.Responses.IntResponse;
import org.example.ConsoleReaders.Responses.StringResponse;
import org.example.ConsoleReaders.StringConsoleReader;
import org.example.collections.CustomArrayList;

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
        IntResponse countResponse = IntConsoleReader.getIntData();
        if (countResponse.state != StringResponse.States.OK){
            System.out.println("Invalid count, returning empty list.");
            return new CustomArrayList<>();
        }
        int count = countResponse.intData;

        CustomArrayList<Car> cars = new CustomArrayList<>();

        IntStream.range(0,count)
                .forEach(i -> {
                    System.out.println("Enter car #" + (i + 1) + " in format: horsepower, model, year");
                    System.out.print("> ");
                    String input = StringConsoleReader.getStringData().stringData;
                    AnyCarBuilder carBuilder = new AnyCarBuilder();
                    carBuilder.setAll(input);
                    Car car = carBuilder.build();
                    if (car != null){
                        cars.add(car);
                    }else{
                        System.out.println("Invalid car data, skipping.");
                    }
                });
        System.out.println("Successfully added " + cars.size() + " valid cars.");
        return cars;
    }

    public static CustomArrayList<Car> fillFromFile(String filePath){
        CustomArrayList<Car> cars = new CustomArrayList<>();
        try(Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.map(CarDeserializer::stringToCar)
                    .filter(car -> car != null && CarValidator.validateCar(car))
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
            AnyCarBuilder carBuilder = new AnyCarBuilder();
            StringBuilder BrandAndModel = new StringBuilder();
            carBuilder.setHorsepower(random.nextInt(MAX_HORSEPOWER_VALUE - MIN_HORSEPOWER_VALUE + 1) + MIN_HORSEPOWER_VALUE);
            BrandAndModel.delete(0, BrandAndModel.length());
            BrandAndModel.append(CARS_BRANDS.get(random.nextInt(CARS_BRANDS.size()))).append(' ');
            int modelLength = random.nextInt(MAX_MODEL_LENGTH) + 1;
            for (int j = 0; j < modelLength; j++) {
                BrandAndModel.append((char)(random.nextInt(26) + 65));
            }
            carBuilder.setModel(BrandAndModel.toString());
            carBuilder.setYear(random.nextInt(Year.now().getValue() - MIN_YEAR_VALUE) + MIN_YEAR_VALUE);
            return carBuilder.build();
        })
                .limit(count)
                .forEach(cars::add);
        return cars;
    }




}
