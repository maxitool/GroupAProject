package org.example.models.car;

import java.time.Year;

public class CarValidator {
    public static boolean validateAll(int horsepower, String model, int year) {
        return validateHorsepower(horsepower) &&
                validateModel(model) &&
                validateYear(year);
    }

    public static boolean validateCar(Car car, boolean doLogOfCarIsNull) {
        if (car == null) {
             if (doLogOfCarIsNull) {
                 System.out.println("Car class is null.");
             }
            return false;
        }
        return validateAll(car.getHorsepower(), car.getModel(), car.getYear());
    }
    public static boolean validateCar(Car car) {
        return validateCar(car, true);
    }

    public static boolean validateHorsepower(int horsepower) {
        if (horsepower < 0) {
            System.out.println("The horsepower of car must be greater than or equal to 0. The horsepower: " + horsepower);
            return false;
        }
        return true;
    }

    public static boolean validateModel(String model) {
        if (model == null) {
            System.out.println("The model of car is null");
            return false;
        }
        model = model.trim();
        if (model.isEmpty()) {
            System.out.println("The model of car is empty");
            return false;
        }
        return true;
    }

    public static boolean validateYear(int year) {
        if (year < 0) {
            System.out.println("The year of car must be greater than or equal to 0. The year: " + year);
            return false;
        }
        if (year > Year.now().getValue()) {
            System.out.println("The year of car must be less than or equal to current year ( " + Year.now().getValue() + " ). The year: " + year);
            return false;
        }
        return true;
    }
}
