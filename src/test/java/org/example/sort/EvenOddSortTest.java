package org.example.sort;

import org.example.models.car.Car;
import org.example.collections.CustomArrayList;
import org.example.sort.comparators.HorsepowerComparator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvenOddSortTest {

    @Test
    void testEvenOddSortByHorsepower() {
        CustomArrayList<Car> cars = new CustomArrayList<>();

        cars.add(new Car(300, "BMW", 2020));
        cars.add(new Car(999, "Lada", 2000));
        cars.add(new Car(100, "Audi", 2018));
        cars.add(new Car(777, "Ford", 2010));
        cars.add(new Car(200, "Tesla", 2022));
        EvenOddSortStrategy sorter = new EvenOddSortStrategy();
        sorter.sort(cars, new HorsepowerComparator());

        assertEquals(100, cars.get(0).getHorsepower());
        assertEquals(200, cars.get(2).getHorsepower());
        assertEquals(300, cars.get(4).getHorsepower());
        assertEquals(999, cars.get(1).getHorsepower());
        assertEquals(777, cars.get(3).getHorsepower());
    }
}