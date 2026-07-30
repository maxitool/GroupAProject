
import org.example.Car;
import org.example.CarCounter;
import org.junit.jupiter.api.Test;
import org.example.collections.CustomArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MultithreadingTest {

    @Test
    public void testCountInSingleThread() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        Car target = new Car(375, "BMW", 2020);

        for (int i = 0; i < 10; i++) {
            cars.add(target);
        }
        for (int i = 0; i < 5; i++) {
            cars.add(new Car(200, "Audi", 2019));
        }
        for (int i = 0; i < 3; i++) {
            cars.add(new Car(300, "Mercedes", 2021));
        }

        CarCounter counter = new CarCounter(cars);

        long count = counter.countOccurrences(target, 1);

        assertEquals(10, count, "Should find exactly 10 occurrences of BMW");
    }

    @Test
    public void testCountInMultipleThreads() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        Car target = new Car(375, "BMW", 2020);

        for (int i = 0; i < 100; i++) {
            cars.add(target);
        }
        for (int i = 0; i < 30; i++) {
            cars.add(new Car(200, "Audi", 2019));
        }

        CarCounter counter = new CarCounter(cars);

        long count = counter.countOccurrences(target, 4);

        assertEquals(100, count, "Should find exactly 100 occurrences of BMW");
    }

    @Test
    public void testResultsMatchAcrossThreads() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        Car target = new Car(375, "BMW", 2020);

        for (int i = 0; i < 50; i++) {
            cars.add(target);
        }
        for (int i = 0; i < 20; i++) {
            cars.add(new Car(200, "Audi", 2019));
        }

        CarCounter counter = new CarCounter(cars);

        long count1 = counter.countOccurrences(target, 1);
        long count2 = counter.countOccurrences(target, 2);
        long count4 = counter.countOccurrences(target, 4);
        long count8 = counter.countOccurrences(target, 8);

        assertEquals(count1, count2, "1 and 2 threads should give the same result");
        assertEquals(count1, count4, "1 and 4 threads should give the same result");
        assertEquals(count1, count8, "1 and 8 threads should give the same result");
        assertEquals(50, count1, "Should find exactly 50 occurrences");
    }

    @Test
    public void testCountWithDifferentElements() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        Car targetBMW = new Car(375, "BMW", 2020);
        Car targetAudi = new Car(200, "Audi", 2019);
        Car targetMercedes = new Car(300, "Mercedes", 2021);

        for (int i = 0; i < 10; i++) {
            cars.add(targetBMW);
        }
        for (int i = 0; i < 20; i++) {
            cars.add(targetAudi);
        }
        for (int i = 0; i < 30; i++) {
            cars.add(targetMercedes);
        }

        CarCounter counter = new CarCounter(cars);

        long countBMW = counter.countOccurrences(targetBMW, 4);
        long countAudi = counter.countOccurrences(targetAudi, 4);
        long countMercedes = counter.countOccurrences(targetMercedes, 4);

        assertEquals(10, countBMW, "BMW should occur 10 times");
        assertEquals(20, countAudi, "Audi should occur 20 times");
        assertEquals(30, countMercedes, "Mercedes should occur 30 times");
    }

    @Test
    public void testCountWithEmptyList() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        Car target = new Car(375, "BMW", 2020);

        CarCounter counter = new CarCounter(cars);

        long count = counter.countOccurrences(target, 4);

        assertEquals(0, count, "Empty list should return 0 occurrences");
    }

    @Test
    public void testCountWithLargeData() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        Car target = new Car(375, "BMW", 2020);

        int totalCars = 10000;
        int targetCount = 5000;

        for (int i = 0; i < targetCount; i++) {
            cars.add(target);
        }
        for (int i = targetCount; i < totalCars; i++) {
            cars.add(new Car(200, "Audi", 2019));
        }

        CarCounter counter = new CarCounter(cars);

        long count = counter.countOccurrences(target, 8);

        assertEquals(targetCount, count, "Should find exactly " + targetCount + " occurrences");
    }


    @Test
    public void testThreadSafety() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        Car target = new Car(375, "BMW", 2020);

        for (int i = 0; i < 100; i++) {
            cars.add(target);
        }

        CarCounter counter = new CarCounter(cars);

        long count1 = counter.countOccurrences(target, 2);
        long count2 = counter.countOccurrences(target, 4);
        long count3 = counter.countOccurrences(target, 8);

        assertEquals(count1, count2, "Count should be consistent across different thread counts");
        assertEquals(count1, count3, "Count should be consistent across different thread counts");
        assertEquals(100, count1, "Should find 100 occurrences");
    }


    @Test
    public void testCountWithMultipleIdenticalElements() {
        CustomArrayList<Car> cars = new CustomArrayList<>();
        Car target = new Car(375, "BMW", 2020);

        for (int i = 0; i < 15; i++) {
            cars.add(target);
        }

        Car otherCar = new Car(375, "BMW", 2020); // такая же машина
        for (int i = 0; i < 10; i++) {
            cars.add(otherCar);
        }

        CarCounter counter = new CarCounter(cars);
        long count = counter.countOccurrences(target, 4);

        assertEquals(25, count, "Should find 25 occurrences of BMW");
    }
}