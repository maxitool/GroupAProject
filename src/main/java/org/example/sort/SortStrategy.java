package org.example.sort;

import org.example.models.car.Car;

import java.util.Comparator;
import java.util.List;


public interface SortStrategy {

    void sort (List<Car> cars, Comparator<Car> comparator);

}
