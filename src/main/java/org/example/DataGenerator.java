package org.example;

import org.example.collections.CustomArrayList;
import org.example.models.car.Car;
import org.example.models.car.CarValidator;

import java.time.Year;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class DataGenerator {
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

    public static CustomArrayList<Car> generateCars(int count) {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        if (count <= 0) {
            System.out.println("The count for generate cars must be greater than 0");
            return cars;
        }
        Random random = new Random();
        Stream.generate(() -> {
                    StringBuilder brandAndModel = new StringBuilder(CARS_BRANDS.get(random.nextInt(CARS_BRANDS.size())) + ' ');
                    int modelLength = random.nextInt(MAX_MODEL_LENGTH) + 1;
                    for (int j = 0; j < modelLength; j++) {
                        brandAndModel.append((char) (random.nextInt(26) + 65));
                    }
                    return Car.builder()
                            .horsepower(random.nextInt(MAX_HORSEPOWER_VALUE - MIN_HORSEPOWER_VALUE + 1) + MIN_HORSEPOWER_VALUE)
                            .model(brandAndModel.toString())
                            .year(random.nextInt(Year.now().getValue() - MIN_YEAR_VALUE) + MIN_YEAR_VALUE)
                            .build();
                })
                .limit(count)
                .forEach(cars::add);
        return cars;
    }
}
