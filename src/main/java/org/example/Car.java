package org.example;

import java.util.Objects;

public class Car {
    public static final int COUNT_FIELDS = 3;
    private int horsepower;
    private String model;
    private int year;

    public Car() {
        horsepower = -1;
        model = "";
        year = -1;
    }

    public Car(int horsepower, String model, int year) {
        this();
        setHorsepower(horsepower).setModel(model).setYear(year);
    }

    public synchronized static Builder builder() { return new Builder(); }

    public int getHorsepower() { return horsepower; }
    public String getModel() { return model; }
    public int getYear() { return year; }

    private Car setHorsepower(int horsepower) {
        this.horsepower = horsepower;
        return this;
    }

    private Car setModel(String model) {
        this.model = model;
        return this;
    }

    private Car setYear(int year) {
        this.year = year;
        return this;
    }

    @Override
    public String toString() {
        return "Car{" +
                "horsepower=" + horsepower + " hp" +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        if (hashCode() != car.hashCode()) return false;
        return horsepower == car.horsepower && year == car.year && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(horsepower, model, year);
    }

    public static class Builder {
        private int horsepower;
        private String model;
        private int year;

        public Builder horsepower(int horsepower) {
            this.horsepower = horsepower;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Car build() {
            Car car = new Car(horsepower, model, year);
            if (CarValidator.validateCar(car)) return car;
            System.out.println("The Car class didn't pass validation.");
            return null;
        }
    }
}
