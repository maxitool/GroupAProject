package org.example.sort;

import org.example.collections.CustomArrayList;
import org.example.models.car.Car;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class EvenOddSortStrategy implements SortStrategy {

    public List<Car> sort (List<Car>cars , Comparator<Car> comparator) {

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

        List<Car> sortedCars = new CustomArrayList<>();
        sortedCars.addAll(cars);

        List<Integer> evenIndices = IntStream.range(0, sortedCars.size())
                .filter(i -> sortedCars.get(i) != null && sortedCars.get(i).getHorsepower() % 2 == 0)
                .boxed()
                .toList();

        List<Car> evenCars = new ArrayList<>(
                evenIndices.stream()
                        .map(sortedCars::get)
                        .toList()
        );

        BubbleSortStrategy bubbleSort = new BubbleSortStrategy();
        bubbleSort.sort(evenCars, comparator);

        IntStream.range(0, evenIndices.size())
                .forEach(i -> sortedCars.set(evenIndices.get(i), evenCars.get(i)));
        return sortedCars;
    }
}