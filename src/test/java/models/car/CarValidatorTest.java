package models.car;

import org.example.models.car.Car;
import org.example.models.car.CarValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Year;

public class CarValidatorTest {

    @Test
    public void validateHorsepower_withGoodData_true() {
        Assertions.assertTrue(CarValidator.validateHorsepower(123));
    }

    @Test
    public void validateHorsepower_withNegativeHorsepower_false() {
        Assertions.assertFalse(CarValidator.validateHorsepower(-123));
    }

    @Test
    public void validateModel_withGoodData_true() {
        Assertions.assertTrue(CarValidator.validateModel("model"));
    }

    @Test
    public void validateModel_withNull_false() {
        Assertions.assertFalse(CarValidator.validateModel(null));
    }

    @Test
    public void validateModel_withEmptyModel_false() {
        Assertions.assertFalse(CarValidator.validateModel(""));
    }

    @Test
    public void validateYear_withGoodYear_true() {
        Assertions.assertTrue(CarValidator.validateYear(Year.now().getValue()));
    }

    @Test
    public void validateYear_withNegativeYear_false() {
        Assertions.assertFalse(CarValidator.validateYear(-23));
    }

    @Test
    public void validateYear_withOlderThanNowYear_false() {
        Assertions.assertFalse(CarValidator.validateYear(Year.now().getValue() + 1));
    }

    @Test
    public void validateCar_withGoodData_true() {
        Car car = Car.builder().horsepower(123).model("test").year(Year.now().getValue()).build();
        Assertions.assertTrue(CarValidator.validateCar(car));
    }

    @Test
    public void validateCar_withBadData_false() {
        Car car = Car.builder().horsepower(-1).model("").year(Year.now().getValue() + 1).build();
        Assertions.assertFalse(CarValidator.validateCar(car));
    }
}
