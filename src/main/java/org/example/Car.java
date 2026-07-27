package org.example;

import java.util.Objects;

public class Car {
    public interface CarInterface {

    }

    private static final int COUNT_FIELDS = 3;
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

    public static Car stringToCar(String data) {
        if (data == null) {
            System.out.println("String data is null");
            return null;
        }
        String dataWithoutCar = data.replace("Car", "").replace("{","").replace("}","").trim();
        String[] dataArray = dataWithoutCar.split(",");
        if (dataArray.length != COUNT_FIELDS) {
            System.out.println("Count fields in Car class must be " + COUNT_FIELDS + ", the received data have " + dataArray.length + " fields");
            return null;
        }
        try {
            return new Car(
                    Integer.parseInt(dataArray[0].replace("horsepower","").replace("=","").replace("hp","").trim()),
                    dataArray[1].replace("model","").replace("=","").replace("'","").trim(),
                    Integer.parseInt(dataArray[2].replace("year","").replace("=","").trim()));
        } catch (NumberFormatException e) {
            System.out.println("Can't convert String data ( " + data + " ) to Car class, reason: " + e.getMessage());
        }
        return null;
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
}
