package org.example.sort;

import org.example.collections.CustomArrayList;
import org.example.models.car.Car;

import java.util.AbstractList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;

public class BubbleSortStrategy implements SortStrategy {

    @Override
    public List<Car> sort(List<Car> cars , Comparator<Car> comparator) {
        if (cars == null) {
            System.out.println("Error: passed list is null");
            return new CustomArrayList<>();
        }
        if (comparator == null) {
            System.out.println("Error: comparator is null");
            return cars;
        }
        if (cars.size() <= 1) {
            return cars;
        }

        List<Car> sortedCars = null;
        try {
            sortedCars =  cars.getClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("Can't create new instance copy of cars");
            return cars;
        }
        sortedCars.addAll(cars);

        int n = sortedCars.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Car current = sortedCars.get(j);
                Car next = sortedCars.get(j + 1);
                if (comparator.compare(current, next) > 0) {
                    sortedCars.set(j, next);
                    sortedCars.set(j + 1, current);
                }
            }
        }
        return sortedCars;
    }
}
