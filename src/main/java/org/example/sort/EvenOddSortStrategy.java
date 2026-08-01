package org.example.sort;

import org.example.models.car.Car;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class EvenOddSortStrategy {

    public void sort (List<Car> cars, Comparator<Car> comparator) {
        if (cars == null || comparator == null || cars.size() <= 1) {
            return;
        }

        List<Integer> evenIndices = IntStream.range(0, cars.size())
                .filter(i -> cars.get(i) != null && cars.get(i).getHorsepower() % 2 == 0)
                .boxed()
                .toList();

        List<Car> evenCars = new ArrayList<>(
                evenIndices.stream()
                        .map(cars::get)
                        .toList()
        );

        BubbleSortStrategy bubbleSort = new BubbleSortStrategy();
        bubbleSort.sort(evenCars, comparator);

        IntStream.range(0, evenIndices.size())
                .forEach(i -> cars.set(evenIndices.get(i), evenCars.get(i)));

    }
}