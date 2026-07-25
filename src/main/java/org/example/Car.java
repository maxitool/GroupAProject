package org.example;

import java.time.Year;
import java.util.Objects;

public class Car {
    private static final int COUNT_FIELDS = 3;
    private boolean isValidationGood;
    private int horsepower;
    private String model;
    private int year;

    public Car() {
        isValidationGood = false;
        horsepower = -1;
        model = "";
        year = -1;
    }
    public Car(int horsepower, String model, int year) {
        this();
        isValidationGood = true;
        setHorsepower(horsepower, false).setModel(model, false).setYear(year, false);
    }

    public boolean getIsValidationGood() { return isValidationGood; }
    public int getHorsepower() { return horsepower; }
    public String getModel() { return model; }
    public int getYear() { return year; }

    private Car setHorsepower(int horsepower, boolean doAnotherValidations) {
        if (!doValidationHorsepower(true, horsepower)) return this;
        this.horsepower = horsepower;
        if (doAnotherValidations) {
            isValidationGood = true;
            doValidationModel(false, model);
            doValidationYear(false, year);
        }
        return this;
    }
    public Car setHorsepower(int horsepower) {
        return setHorsepower(horsepower, true);
    }

    private Car setModel(String model, boolean doAnotherValidations) {
        if (!doValidationModel(true, model)) return this;
        this.model = model;
        if (doAnotherValidations) {
            isValidationGood = true;
            doValidationHorsepower(false, horsepower);
            doValidationYear(false, year);
        }
        return this;
    }
    public Car setModel(String model) {
        return  setModel(model, true);
    }

    private Car setYear(int year, boolean doAnotherValidations) {
        if (!doValidationYear(true, year)) return this;
        this.year = year;
        if (doAnotherValidations) {
            isValidationGood = true;
            doValidationHorsepower(false, horsepower);
            doValidationModel(false, model);
        }
        return this;
    }
    public Car setYear(int year) {
        return setYear(year, true);
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

    private boolean doValidationHorsepower(boolean isPrintInfoToConsole, int horsepower) {
        if (horsepower < 0) {
            if (isPrintInfoToConsole) System.out.println("The horsepower of car must be greater than or equal to 0. The horsepower: " + horsepower);
            isValidationGood = false;
            return false;
        }
        return true;
    }
    private boolean doValidationModel(boolean isPrintInfoToConsole, String model) {
        if (model == null || model.isEmpty()) {
            if (isPrintInfoToConsole) System.out.println("The model of car is null or empty");
            isValidationGood = false;
            return false;
        }
        return true;
    }
    private boolean doValidationYear(boolean isPrintInfoToConsole, int year) {
        if (year < 0) {
            if (isPrintInfoToConsole) System.out.println("The year of car must be greater than or equal to 0. The year: " + year);
            isValidationGood = false;
            return false;
        }
        if (year > Year.now().getValue()) {
            if (isPrintInfoToConsole) System.out.println("The year of car must be less than or equal to current year ( " + Year.now().getValue() + " ). The year: " + year);
            isValidationGood = false;
            return false;
        }
        return true;
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
