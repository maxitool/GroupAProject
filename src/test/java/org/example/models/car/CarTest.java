package org.example.models.car;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Year;

public class CarTest {

    @Test
    public void when_buildBuilderWithGoodData_then_returnCar() {
        Car expectedCar = new Car(123, "test", Year.now().getValue());
        Car realCar = Car.builder().horsepower(123).model("test").year(Year.now().getValue()).build();
        Assertions.assertEquals(expectedCar, realCar);
    }


    @Test
    public void when_buildBuilderWithNegativeHorsepower_then_returnNull() {
        Car realCar = Car.builder().horsepower(-1).model("test").year(1990).build();
        Assertions.assertNull(realCar);
    }

    @Test
    public void when_buildBuilderWithNullModel_then_returnNull() {
        Car realCar = Car.builder().horsepower(123).model(null).year(1990).build();
        Assertions.assertNull(realCar);
    }

    @Test
    public void when_buildBuilderWithEmptyModel_then_returnNull() {
        Car realCar = Car.builder().horsepower(123).model("").year(1990).build();
        Assertions.assertNull(realCar);
    }

    @Test
    public void when_buildBuilderWithNegativeYear_then_returnNull() {
        Car realCar = Car.builder().horsepower(123).model("test").year(-1).build();
        Assertions.assertNull(realCar);
    }

    @Test
    public void when_buildBuilderWithOlderThanNowYear_then_returnNull() {
        Car realCar = Car.builder().horsepower(123).model("test").year(Year.now().getValue() + 1).build();
        Assertions.assertNull(realCar);
    }
}
