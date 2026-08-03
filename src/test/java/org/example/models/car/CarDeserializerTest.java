package org.example.models.car;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CarDeserializerTest {

    @Test
    public void when_stringToCarWithGoodData_then_returnCar() {
        String stringCar1 = "Car{horsepower=290 hp, model='Chrysler PYR', year=1974}";
        Car car = new Car(290, "Chrysler PYR", 1974);
        Assertions.assertEquals(car, CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void when_stringToCarWithoutCarShell_then_returnCar() {
        String stringCar1 = "horsepower=290 hp, model='Chrysler PYR', year=1974";
        Car car = new Car(290, "Chrysler PYR", 1974);
        Assertions.assertEquals(car, CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void when_stringToCarWithGoodDataWithoutSpaces_then_returnCar() {
        String stringCar1 = "Car{horsepower=290 hp,model='Chrysler PYR',year=1974}";
        Car car = new Car(290, "Chrysler PYR", 1974);
        Assertions.assertEquals(car, CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void when_stringToCarWithMoreKeys_then_returnCar() {
        String stringCar1 = "Car{horsepower=290 hp, model='Chrysler PYR', year=1974, key1=1, key2='value'}";
        Car car = new Car(290, "Chrysler PYR", 1974);
        Assertions.assertEquals(car, CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void when_stringToCarWithoutHpForHorsepower_then_returnCar() {
        String stringCar1 = "Car{horsepower=290,model='Chrysler PYR',year=1974}";
        Car car = new Car(290, "Chrysler PYR", 1974);
        Assertions.assertEquals(car, CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void when_stringToCarWithWithModelFormat1_then_returnCar() {
        String stringCar1 = "Car{horsepower=290 hp, model=Chrysler PYR, year=1974}";
        Car car = new Car(290, "Chrysler PYR", 1974);
        Assertions.assertEquals(car, CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void when_stringToCarWithWithModelFormat2_then_returnCar() {
        String stringCar1 = "Car{horsepower=290 hp, model=\"Chrysler PYR\", year=1974}";
        Car car = new Car(290, "Chrysler PYR", 1974);
        Assertions.assertEquals(car, CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void when_stringToCarWithWithModelFormat3_then_returnCar() {
        String stringCar1 = "Car{horsepower=290 hp, model='Chrysler PYR\", year=1974}";
        Car car = new Car(290, "Chrysler PYR", 1974);
        Assertions.assertEquals(car, CarDeserializer.stringToCar(stringCar1));
    }


    @Test
    public void when_stringToCarWithNull_then_returnNull() {
        String stringCar1 = null;
        Assertions.assertNull(CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void when_stringToCarWithEmpty_then_returnNull() {
        String stringCar1 = "";
        Assertions.assertNull(CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void when_stringToCarWithNoKeys_then_returnNull() {
        String stringCar1 = "Car{290 hp, 'Chrysler PYR', 1974}";
        Assertions.assertNull(CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void when_stringToCarNoHorsepowerKey_then_returnNull() {
        String stringCar1 = "Car{model='Chrysler PYR', year=1974}";
        Assertions.assertNull(CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void when_stringToCarNoModelKey_then_returnNull() {
        String stringCar1 = "Car{horsepower=290 hp, year=1974}";
        Assertions.assertNull(CarDeserializer.stringToCar(stringCar1));
    }

    @Test
    public void when_stringToCarNoYearKey_then_returnNull() {
        String stringCar1 = "Car{horsepower=290 hp, model='Chrysler PYR'}";
        Assertions.assertNull(CarDeserializer.stringToCar(stringCar1));
    }
}
