package org.example.sort.comparators;

import org.example.models.car.Car;

import java.util.Comparator;

public class ModelComparator implements Comparator<Car> {

    @Override
    public int compare(Car o1, Car o2) {
        if (o1 == o2) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;

        String m1 = o1.getModel();
        String m2 = o2.getModel();

        if (m1 == m2) return 0;
        if (m1 == null) return -1;
        if (m2 == null) return 1;

        return m1.compareTo(m2);
    }
}