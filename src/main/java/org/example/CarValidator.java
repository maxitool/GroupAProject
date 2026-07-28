package org.example;

import java.time.Year;

public class CarValidator {
    public static boolean validateAll(int horsepower, String model, int year,  boolean isPrintInfoToConsole) {
        return validateHorsepower(horsepower, isPrintInfoToConsole) &&
                validateModel(model, isPrintInfoToConsole) &&
                validateYear(year, isPrintInfoToConsole);
    }
    public static boolean validateAll(int horsepower, String model, int year) {
        return validateAll(horsepower, model, year,  true);
    }

    public static boolean validateCar(Car car, boolean isPrintInfoToConsole) {
        if (car == null) {
            if (isPrintInfoToConsole) System.out.println("Car class is null.");
            return false;
        }
        return validateAll(car.getHorsepower(), car.getModel(), car.getYear(), isPrintInfoToConsole);
    }
    public static boolean validateCar(Car car) { return validateCar(car, true); }

    public static boolean validateHorsepower(int horsepower, boolean isPrintInfoToConsole) {
        if (horsepower < 0) {
            if (isPrintInfoToConsole) System.out.println("The horsepower of car must be greater than or equal to 0. The horsepower: " + horsepower);
            return false;
        }
        return true;
    }
    public static boolean validateHorsepower(int horsepower) { return validateHorsepower(horsepower, true); }

    public static boolean validateModel(String model, boolean isPrintInfoToConsole) {
        if (model == null) {
            if (isPrintInfoToConsole) System.out.println("The model of car is null");
            return false;
        }
        model = model.trim();
        if (model.isEmpty()) {
            if (isPrintInfoToConsole) System.out.println("The model of car is empty");
            return false;
        }
        return true;
    }
    public static boolean validateModel(String model) { return validateModel(model, true); }

    public static boolean validateYear(int year, boolean isPrintInfoToConsole) {
        if (year < 0) {
            if (isPrintInfoToConsole) System.out.println("The year of car must be greater than or equal to 0. The year: " + year);
            return false;
        }
        if (year > Year.now().getValue()) {
            if (isPrintInfoToConsole) System.out.println("The year of car must be less than or equal to current year ( " + Year.now().getValue() + " ). The year: " + year);
            return false;
        }
        return true;
    }
    public static boolean validateYear(int year) { return validateYear(year, true); }
}
