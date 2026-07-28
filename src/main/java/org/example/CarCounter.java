package org.example;

import java.util.ArrayList;
import java.util.List;

public class CarCounter {
    private final List<Car> cars;

    public CarCounter(List<Car> cars) {
        this.cars = cars;
    }
    public long countOccurrences(Car target, int threadCount) {
        if (cars==null || cars.isEmpty()) {
            System.out.println("Коллекция пуста");
            return 0;
        }
        if (threadCount <= 0 ) threadCount = 1;
        if (threadCount > cars.size()) threadCount = cars.size();
        System.out.println("Подсчет в " + threadCount + " потоках");
        int size = cars.size();
        int partSize = size / threadCount;
        int remainder = size % threadCount;
        int start = 0;
        List <Thread> threads = new ArrayList<>();
        List <Long> results = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            int end = start + partSize + (i < remainder? 1 : 0);
            List <Car> subList  =  cars.subList(start,end);
            start = end;
            Runnable  task = () -> {
                long count = 0 ;
                for (Car car : subList) {
                    if (cars.equals(target)) count++;
                }
                synchronized (results) {
                    results.add(count);

                }
            };
            Thread thread =  new Thread(task);
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
        long total = 0;
        for (long count : results){
            total+= count;

        }
        System.out.println("Найдено " + total);
        return total;
    }
    public void printOccurrences(Car target, int threadCount) {
        long count = countOccurrences(target, threadCount);
        System.out.println("Результат: " + count + " вхождений");
    }
}
