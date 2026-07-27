package org.example;

import org.example.ConsoleReaders.IntConsoleReader;
import org.example.ConsoleReaders.Responses.IntResponse;
import org.example.ConsoleReaders.Responses.StringResponse;
import org.example.ConsoleReaders.StringConsoleReader;
import org.example.collections.CustomArrayList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CarFiller {
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
                    Car car = Car.stringToCar(input);
                    if (car != null && car.getIsValidationGood()){
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
            lines.map(Car::stringToCar)
                    .filter(car -> car != null && car.getIsValidationGood())
                    .forEach(cars::add);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return cars;
    }

    public static CustomArrayList<Car> fillRandom(int count){
        Random random = new Random();
        String[] models = {
                "BMW 7 Series 740i",
                "BMW X1 xDrive28i",
                "BMW X3 3.0i",
                "BMW 4 Series Gran Coupe",
                "BMW X1 xDrive28i",
                "BMW X5 M Base",
                "BMW 8 Series Gran Coupe"};

        CustomArrayList<Car> cars = new CustomArrayList<>();
        Stream.generate(() -> {
            int hp = random.nextInt(500) + 50;
            String model = models[random.nextInt(models.length)];
            int year = 2000 + random.nextInt(26);
            return new Car(hp, model, year);
        })
                .limit(count)
                .filter(Car::getIsValidationGood)
                .forEach(cars::add);
        return cars;
    }




}
