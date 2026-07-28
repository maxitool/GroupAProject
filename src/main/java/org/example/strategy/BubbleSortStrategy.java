package org.example.strategy;

import org.example.Car;

import java.util.Comparator;
import java.util.List;

public class BubbleSortStrategy implements SortStrategy {

    @Override
    public void sort(List<Car> cars, Comparator<Car> comparator) {

        if (cars == null) {

            System.out.println("Ошибка: переданный список равен null");
            return;

        }

        if (comparator == null) {

            System.out.println("Ошибка: компаратор равен null");
            return;

        }

        if (cars.size() <= 1) {

            return;

        }

        int n = cars.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {

                Car current = cars.get(j);
                Car next = cars.get(j + 1);

                if (comparator.compare(current, next) > 0) {

                    cars.set(j, next);
                    cars.set(j + 1, current);

                }

            }

        }

    }
}
