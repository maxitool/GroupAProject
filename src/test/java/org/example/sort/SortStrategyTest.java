package org.example.sort;

import org.example.collections.CustomArrayList;
import org.example.models.car.Car;
import org.example.sort.comparators.HorsepowerComparator;
import org.example.sort.comparators.ModelComparator;
import org.example.sort.comparators.YearComparator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortStrategyTest {

    @Test
    void when_testSortByHorsepower_then_returnCorrectSortedCars() {
        List<Car> cars = new CustomArrayList<>();
        cars.add(new Car(300, "BMW", 2020));
        cars.add(new Car(100, "Audi", 2018));
        cars.add(new Car(250, "Porsche", 2021));
        cars.add(new Car(200, "Tesla", 2022));

        BubbleSortStrategy sorter = new BubbleSortStrategy();
        cars = sorter.sort(cars, new HorsepowerComparator());

        assertEquals(100, cars.get(0).getHorsepower());
        assertEquals(200, cars.get(1).getHorsepower());
        assertEquals(250, cars.get(2).getHorsepower());
        assertEquals(300, cars.get(3).getHorsepower());
    }

    @Test
    void when_testSortByYear_then_returnCorrectSortedCars() {
        List<Car> cars = new CustomArrayList<>();
        cars.add(new Car(200, "Tesla", 2022));
        cars.add(new Car(100, "Audi", 2018));
        cars.add(new Car(300, "BMW", 2020));

        BubbleSortStrategy sorter = new BubbleSortStrategy();
        cars = sorter.sort(cars, new YearComparator());

        assertEquals(2018, cars.get(0).getYear());
        assertEquals(2020, cars.get(1).getYear());
        assertEquals(2022, cars.get(2).getYear());
    }

    @Test
    void when_testSortByModel_then_returnCorrectSortedCars() {
        List<Car> cars = new CustomArrayList<>();
        cars.add(new Car(200, "Tesla", 2022));
        cars.add(new Car(100, "Audi", 2018));
        cars.add(new Car(300, "BMW", 2020));

        BubbleSortStrategy sorter = new BubbleSortStrategy();
        cars = sorter.sort(cars, new ModelComparator());

        assertEquals("Audi", cars.get(0).getModel());
        assertEquals("BMW", cars.get(1).getModel());
        assertEquals("Tesla", cars.get(2).getModel());
    }
}