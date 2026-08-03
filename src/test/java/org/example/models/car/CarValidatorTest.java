package org.example.models.car;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Year;

public class CarValidatorTest {

    @Test
    public void when_validateHorsepowerWithGoodData_then_returnTrue() {
        Assertions.assertTrue(CarValidator.validateHorsepower(123));
    }

    @Test
    public void when_validateHorsepowerWithNegativeHorsepower_then_returnFalse() {
        Assertions.assertFalse(CarValidator.validateHorsepower(-123));
    }

    @Test
    public void when_validateModelWithGoodData_then_returnTrue() {
        Assertions.assertTrue(CarValidator.validateModel("model"));
    }

    @Test
    public void when_validateModelWithNull_then_returnFalse() {
        Assertions.assertFalse(CarValidator.validateModel(null));
    }

    @Test
    public void when_validateModelWithEmpty_then_returnFalse() {
        Assertions.assertFalse(CarValidator.validateModel(""));
    }

    @Test
    public void when_validateYearWithGoodYear_then_returnTrue() {
        Assertions.assertTrue(CarValidator.validateYear(Year.now().getValue()));
    }

    @Test
    public void when_validateYearWithNegativeYear_then_returnFalse() {
        Assertions.assertFalse(CarValidator.validateYear(-23));
    }

    @Test
    public void when_validateYearWithOlderThanNowYear_then_returnFalse() {
        Assertions.assertFalse(CarValidator.validateYear(Year.now().getValue() + 1));
    }

    @Test
    public void when_validateCarWithGoodData_then_returnTrue() {
        Car car = Car.builder().horsepower(123).model("test").year(Year.now().getValue()).build();
        Assertions.assertTrue(CarValidator.validateCar(car));
    }

    @Test
    public void when_validateCarWithBadData_then_returnFalse() {
        Car car = Car.builder().horsepower(-1).model("").year(Year.now().getValue() + 1).build();
        Assertions.assertFalse(CarValidator.validateCar(car));
    }
}
