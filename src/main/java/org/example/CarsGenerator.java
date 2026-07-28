package org.example;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarsGenerator implements FillCarsStrategy {
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

    @Override
    public List<Car> getCars(int countCars) {
        List<Car> cars = new ArrayList<>();
        AnyCarBuilder carBuilder = new AnyCarBuilder();
        StringBuilder BrandAndModel = new StringBuilder();
        int modelLength;
        Random random = new Random();
        for (int i = 0; i < countCars; i++) {
            carBuilder.setHorsepower(random.nextInt(MAX_HORSEPOWER_VALUE - MIN_HORSEPOWER_VALUE + 1) + MIN_HORSEPOWER_VALUE);
            BrandAndModel.delete(0, BrandAndModel.length());
            BrandAndModel.append(CARS_BRANDS.get(random.nextInt(CARS_BRANDS.size()))).append(' ');
            modelLength = random.nextInt(MAX_MODEL_LENGTH) + 1;
            for (int j = 0; j < modelLength; j++) {
                BrandAndModel.append((char)(random.nextInt(26) + 65));
            }
            carBuilder.setModel(BrandAndModel.toString());
            carBuilder.setYear(random.nextInt(Year.now().getValue() - MIN_YEAR_VALUE) + MIN_YEAR_VALUE);
            cars.add(carBuilder.build());
        }
        return cars;
    }
}
