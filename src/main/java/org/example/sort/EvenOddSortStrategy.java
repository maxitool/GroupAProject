package org.example.sort;

import org.example.collections.CustomArrayList;
import org.example.models.car.Car;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class EvenOddSortStrategy implements SortStrategy {

    public List<Car> sort(List<Car> cars, Comparator<Car> comparator) {
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
            sortedCars = cars.getClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("Can't create new instance copy of cars");
            return cars;
        }
        sortedCars.addAll(cars);
        List<Car> streamSortedCars = sortedCars;

        List<Integer> evenIndices = IntStream.range(0, sortedCars.size())
                .filter(i -> streamSortedCars.get(i) != null && streamSortedCars.get(i).getHorsepower() % 2 == 0)
                .boxed()
                .toList();
        List<Car> evenCars = new ArrayList<>(
                evenIndices.stream()
                        .map(sortedCars::get)
                        .toList()
        );

        BubbleSortStrategy bubbleSort = new BubbleSortStrategy();
        List<Car> streamEvenCars = bubbleSort.sort(evenCars, comparator);

        IntStream.range(0, evenIndices.size())
                .forEach(i -> streamSortedCars.set(evenIndices.get(i), streamEvenCars.get(i)));
        return streamSortedCars;
    }
}