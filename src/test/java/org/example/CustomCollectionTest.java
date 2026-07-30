package org.example;

import org.example.models.car.Car;
import org.example.collections.CustomArrayList;

public class CustomCollectionTest {
    public static void main(String[] args) {
        CustomArrayList<Car> list = new CustomArrayList<>();
        System.out.println("Initial size: " + list.size()); // 0
        Car car1 = new Car(100, "Test1", 2020);
        Car car2 = new Car(200, "Test2", 2021);
        Car car3 = new Car(300, "Test3", 2012);
        list.add(car1);
        list.add(car2);
        list.add(car3);
        System.out.println("Size after add: " + list.size()); // 2
        System.out.println("Element at 0: " + list.get(0));
        System.out.println("Element at 1: " + list.get(1));
        System.out.println("Element at 2: " + list.get(2));
        Car old = list.set(1, car3);
        System.out.println("Removed: " + old);
        for(Car c : list){
            System.out.println("\nTest iteration after set: "+ c);
        }
        try {
            list.set(10, car1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("\nIndexOutOfBoundsException: " + e.getMessage());
        }
        try {
            list.get(5);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("\nIndexOutOfBoundsException caught: " + e.getMessage());
        }

    }
}