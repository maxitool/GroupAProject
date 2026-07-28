package org.example;

public interface CarBuilder {
    CarBuilder setAll(String car);
    CarBuilder setHorsepower(int horsepower);
    CarBuilder setModel(String model);
    CarBuilder setYear(int year);
    int getHorsepower();
    String getModel();
    int getYear();
    Car build();
}
