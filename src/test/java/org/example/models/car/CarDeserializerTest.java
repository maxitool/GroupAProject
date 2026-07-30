package org.example.models.car;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CarDeserializerTest {

    @Test
    public void stringToCar_withGoodData_car() {
        String stringCar1 = "Car{horsepower=290 hp, model='Chrysler PYR', year=1974}";
        Car car = new Car(290, "Chrysler PYR", 1974);
        Assertions.assertEquals(car, CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void stringToCar_withGoodDataWithoutCarShell_car() {
        String stringCar1 = "horsepower=290 hp, model='Chrysler PYR', year=1974";
        Car car = new Car(290, "Chrysler PYR", 1974);
        Assertions.assertEquals(car, CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void stringToCar_withGoodDataWithoutSpaces_car() {
        String stringCar1 = "Car{horsepower=290 hp,model='Chrysler PYR',year=1974}";
        Car car = new Car(290, "Chrysler PYR", 1974);
        Assertions.assertEquals(car, CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void stringToCar_withMoreKeys_car() {
        String stringCar1 = "Car{horsepower=290 hp, model='Chrysler PYR', year=1974, key1=1, key2='value'}";
        Car car = new Car(290, "Chrysler PYR", 1974);
        Assertions.assertEquals(car, CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void stringToCar_withGoodDataWithoutHpForHorsepower_car() {
        String stringCar1 = "Car{horsepower=290,model='Chrysler PYR',year=1974}";
        Car car = new Car(290, "Chrysler PYR", 1974);
        Assertions.assertEquals(car, CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void stringToCar_withGoodDataWithModelFormat1_car() {
        String stringCar1 = "Car{horsepower=290 hp, model=Chrysler PYR, year=1974}";
        Car car = new Car(290, "Chrysler PYR", 1974);
        Assertions.assertEquals(car, CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void stringToCar_withGoodDataWithModelFormat2_car() {
        String stringCar1 = "Car{horsepower=290 hp, model=\"Chrysler PYR\", year=1974}";
        Car car = new Car(290, "Chrysler PYR", 1974);
        Assertions.assertEquals(car, CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void stringToCar_withGoodDataWithModelFormat3_car() {
        String stringCar1 = "Car{horsepower=290 hp, model='Chrysler PYR\", year=1974}";
        Car car = new Car(290, "Chrysler PYR", 1974);
        Assertions.assertEquals(car, CarDeserializer.stringToCar(stringCar1));
    }


    @Test
    public void stringToCar_withNull_null() {
        String stringCar1 = null;
        Assertions.assertNull(CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void stringToCar_withEmpty_null() {
        String stringCar1 = "";
        Assertions.assertNull(CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void stringToCar_noKeys_null() {
        String stringCar1 = "Car{290 hp, 'Chrysler PYR', 1974}";
        Assertions.assertNull(CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void stringToCar_withNoHorsepowerKey_null() {
        String stringCar1 = "Car{model='Chrysler PYR', year=1974}";
        Assertions.assertNull(CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void stringToCar_withNoModelKey_null() {
        String stringCar1 = "Car{horsepower=290 hp, year=1974}";
        Assertions.assertNull(CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void stringToCar_withNoYearKey_null() {
        String stringCar1 = "Car{horsepower=290 hp, model='Chrysler PYR'}";
        Assertions.assertNull(CarDeserializer.stringToCar(stringCar1));
    }
}
