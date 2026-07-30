package org.example.strategy;

import org.example.models.car.Car;
import org.example.comparator.HorsepowerComparator;
import org.example.comparator.ModelComparator;
import org.example.comparator.YearComparator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortStrategyTest {

    @Test
    void testSortByHorsepower() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car(300, "BMW", 2020));
        cars.add(new Car(100, "Audi", 2018));
        cars.add(new Car(250, "Porsche", 2021));
        cars.add(new Car(200, "Tesla", 2022));

        BubbleSortStrategy sorter = new BubbleSortStrategy();
        sorter.sort(cars, new HorsepowerComparator());

        // Проверяем порядок по мощности: 100 -> 200 -> 250 -> 300
        assertEquals(100, cars.get(0).getHorsepower());
        assertEquals(200, cars.get(1).getHorsepower());
        assertEquals(250, cars.get(2).getHorsepower());
        assertEquals(300, cars.get(3).getHorsepower());
    }

    @Test
    void testSortByYear() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car(200, "Tesla", 2022));
        cars.add(new Car(100, "Audi", 2018));
        cars.add(new Car(300, "BMW", 2020));

        BubbleSortStrategy sorter = new BubbleSortStrategy();
        sorter.sort(cars, new YearComparator());

        // Проверяем порядок по году: 2018 -> 2020 -> 2022
        assertEquals(2018, cars.get(0).getYear());
        assertEquals(2020, cars.get(1).getYear());
        assertEquals(2022, cars.get(2).getYear());
    }

    @Test
    void testSortByModel() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car(200, "Tesla", 2022));
        cars.add(new Car(100, "Audi", 2018));
        cars.add(new Car(300, "BMW", 2020));

        BubbleSortStrategy sorter = new BubbleSortStrategy();
        sorter.sort(cars, new ModelComparator());

        // Проверяем алфавитный порядок моделей: "Audi" -> "BMW" -> "Tesla"
        assertEquals("Audi", cars.get(0).getModel());
        assertEquals("BMW", cars.get(1).getModel());
        assertEquals("Tesla", cars.get(2).getModel());
    }
}