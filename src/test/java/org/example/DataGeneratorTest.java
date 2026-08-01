package org.example;

import org.example.collections.CustomArrayList;
import org.example.models.car.Car;
import org.example.models.car.CarValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataGeneratorTest {

    @Test
    void generateCars_withPositiveCount_returnsCollectionOfSpecifiedSize() {
        CustomArrayList<Car> cars = DataGenerator.generateCars(10);
        assertEquals(10, cars.size(), "Должно быть сгенерировано ровно 10 машин");
        assertFalse(cars.isEmpty(), "Коллекция не должна быть пустой");
    }

    @Test
    void generateCars_withZeroCount_returnsEmptyCollection() {
        CustomArrayList<Car> cars = DataGenerator.generateCars(0);
        assertEquals(0, cars.size(), "При нулевом количестве должна быть пустая коллекция");
        assertTrue(cars.isEmpty(), "Коллекция должна быть пустой");
    }

    @Test
    void generateCars_withNegativeCount_returnsEmptyCollection() {
        CustomArrayList<Car> cars = DataGenerator.generateCars(-5);
        assertEquals(0, cars.size(), "При отрицательном количестве должна быть пустая коллекция");
        assertTrue(cars.isEmpty(), "Коллекция должна быть пустой");
    }

    @Test
    void generateCars_always_returnsOnlyValidCars() {
        CustomArrayList<Car> cars = DataGenerator.generateCars(100);
        for (Car car : cars) {
            assertTrue(CarValidator.validateCar(car),
                    "Все сгенерированные машины должны быть валидными: " + car);
        }
    }

    @Test
    void generateCars_always_horsepowerInRange50to500() {
        CustomArrayList<Car> cars = DataGenerator.generateCars(50);
        for (Car car : cars) {
            int hp = car.getHorsepower();
            assertTrue(hp >= 50 && hp <= 500,
                    "Мощность должна быть между 50 и 500, но была: " + hp);
        }
    }

    @Test
    void generateCars_always_yearInRange2000to2025() {
        CustomArrayList<Car> cars = DataGenerator.generateCars(50);
        boolean isTestPass = true;
        for (Car car : cars) {
            int year = car.getYear();
            isTestPass = isTestPass && (CarValidator.validateYear(car.getYear()));
        }
        assertTrue(isTestPass, "Год должен быть между 2000 и 2025");
    }

    @Test
    void generateCars_always_modelIsNotEmpty() {
        CustomArrayList<Car> cars = DataGenerator.generateCars(20);
        for (Car car : cars) {
            assertNotNull(car.getModel(), "Модель не должна быть null");
            assertFalse(car.getModel().isEmpty(), "Модель не должна быть пустой");
            assertFalse(car.getModel().isBlank(), "Модель не должна содержать только пробелы");
        }
    }
}