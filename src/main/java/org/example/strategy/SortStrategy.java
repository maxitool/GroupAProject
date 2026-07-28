package org.example.strategy;

import org.example.Car;

import java.util.Comparator;
import java.util.List;


public interface SortStrategy {

    void sort (List<Car> cars, Comparator<Car> comparator);

}
