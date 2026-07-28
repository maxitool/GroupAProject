package org.example;

public class AnyCarBuilder implements CarBuilder{
    private int horsepower;
    private String model;
    private int year;

    @Override
    public CarBuilder setHorsepower(int horsepower) {
        this.horsepower = horsepower;
        return null;
    }

    @Override
    public CarBuilder setModel(String model) {
        this.model = model;
        return null;
    }

    @Override
    public CarBuilder setYear(int year) {
        this.year = year;
        return null;
    }

    @Override
    public int getHorsepower() {
        return horsepower;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public Car build() {
        Car car = new Car(horsepower, model, year);
        if (CarValidator.validateCar(car)) return car;
        System.out.println("The car class didn't pass validation.");
        return null;
    }
}
