package org.example.comparator;

import org.example.Car;

import java.util.Comparator;

public class YearComparator implements Comparator<Car> {


    @Override
    public int compare(Car o1, Car o2) {

        if (o1 == o2) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;

        return Integer.compare(o1.getYear(), o2.getYear());
    }
}