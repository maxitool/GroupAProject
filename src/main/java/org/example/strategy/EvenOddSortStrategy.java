package org.example.strategy;

import org.example.Car;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EvenOddSortStrategy {

    public void sortEvenOnly(List<Car> cars, Comparator<Car> comparator) {

        if (cars == null || comparator == null || cars.size() <= 1) {
            return;
        }

        List<Car> evenCars = new ArrayList<>();
        List<Integer> evenIndices = new ArrayList<>();

        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);

            if (car != null) {
                if (car.getHorsepower() % 2 == 0) {
                    evenCars.add(car);
                    evenIndices.add(i);
                }

            }

        }

        BubbleSortStrategy bubbleSort = new BubbleSortStrategy();
        bubbleSort.sort(evenCars, comparator);

        for (int i = 0; i < evenIndices.size(); i++) {
            int originalIndex = evenIndices.get(i);
            Car sortedCar = evenCars.get(i);

            cars.set(originalIndex, sortedCar);
        }

    }
}