package org.example;

import org.example.models.car.Car;

import java.util.ArrayList;
import java.util.List;

public class CarCounter {
    private final List<Car> cars;

    public CarCounter(List<Car> cars) {
        this.cars = cars;
    }

    public long countOccurrences(Car target, int threadCount) {
        if (cars == null || cars.isEmpty()) {
            System.out.println("The collection is empty.");
            return 0;
        }
        int maxThreads = Runtime.getRuntime().availableProcessors();
        System.out.println("Available cores: " + maxThreads);

        if (threadCount <= 0) {
            threadCount = 1;
        }
        if (threadCount > maxThreads) {
            System.out.println("Requested " + threadCount + " threads, but only " + maxThreads + " are available.");
            System.out.println("Using " + maxThreads + " threads.");
            threadCount = maxThreads;
        }
        if (threadCount > cars.size()) {
            System.out.println("More threads than cars. Limiting to " + cars.size() + " threads.");
            threadCount = cars.size();
        }
        System.out.println("Counting in " + threadCount + " threads...");
        int size = cars.size();
        int partSize = size / threadCount;
        int remainder = size % threadCount;
        int start = 0;
        List<Thread> threads = new ArrayList<>();
        List<Long> results = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            int end = start + partSize + (i < remainder ? 1 : 0);
            List<Car> subList = cars.subList(start, end);
            start = end;
            Runnable task = () -> {
                long count = 0;
                for (Car car : subList) {
                    if (car.equals(target)) {
                        count++;
                    }
                }
                synchronized (results) {
                    results.add(count);
                }
            };
            Thread thread = new Thread(task);
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        long total = 0;
        for (long count : results) {
            total += count;
        }
        return total;
    }

    public void printOccurrences(Car target, int threadCount) {
        long count = countOccurrences(target, threadCount);
        System.out.println("Result: " + count + " occurrences.");
    }
}
