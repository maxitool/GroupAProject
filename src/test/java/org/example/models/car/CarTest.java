package org.example.models.car;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Year;

public class CarTest {

    @Test
    public void builder_build_withGoodData_car() {
        Car expectedCar = new Car(123, "test", Year.now().getValue());
        Car realCar = Car.builder().horsepower(123).model("test").year(Year.now().getValue()).build();
        Assertions.assertEquals(expectedCar, realCar);
    }


    @Test
    public void builder_build_withNegativeHorsepower_null() {
        Car realCar = Car.builder().horsepower(-1).model("test").year(1990).build();
        Assertions.assertNull(realCar);
    }

    @Test
    public void builder_build_withNullModel_null() {
        Car realCar = Car.builder().horsepower(123).model(null).year(1990).build();
        Assertions.assertNull(realCar);
    }

    @Test
    public void builder_build_withEmptyModel_null() {
        Car realCar = Car.builder().horsepower(123).model("").year(1990).build();
        Assertions.assertNull(realCar);
    }

    @Test
    public void builder_build_withNegativeYear_null() {
        Car realCar = Car.builder().horsepower(123).model("test").year(-1).build();
        Assertions.assertNull(realCar);
    }

    @Test
    public void builder_build_withOlderThanNowYear_null() {
        Car realCar = Car.builder().horsepower(123).model("test").year(Year.now().getValue() + 1).build();
        Assertions.assertNull(realCar);
    }
}
