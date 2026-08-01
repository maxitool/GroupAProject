package org.example;

import org.example.models.car.Car;
import org.example.collections.CustomArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class CustomCollectionTest {

    private CustomArrayList<Car> list;
    private Car car1;
    private Car car2;
    private Car car3;

    @BeforeEach
    void setUp() {
        list = new CustomArrayList<>();
        car1 = new Car(100, "Test1", 2020);
        car2 = new Car(200, "Test2", 2021);
        car3 = new Car(300, "Test3", 2022);
        list.add(car1);
        list.add(car2);
        list.add(car3);
    }

    @Test
    void testInitialSize_emptyCollection_sizeIsZero() {
        CustomArrayList<Car> emptyList = new CustomArrayList<>();
        assertEquals(0, emptyList.size());
        assertTrue(emptyList.isEmpty());
    }

    @Test
    void testAdd_addThreeCars_sizeIsThree() {
        assertEquals(3, list.size());
        assertFalse(list.isEmpty());
    }

    @Test
    void testGet_getElements_returnsCorrectCars() {
        assertEquals(car1, list.get(0));
        assertEquals(car2, list.get(1));
        assertEquals(car3, list.get(2));
        assertEquals(100, list.get(0).getHorsepower());
        assertEquals("Test1", list.get(0).getModel());
        assertEquals(2020, list.get(0).getYear());
    }

    @Test
    void testSet_replaceElement_returnsOldElement() {
        Car newCar = new Car(999, "NewTest", 2025);
        Car old = list.set(1, newCar);
        assertEquals(car2, old);
        assertEquals(200, old.getHorsepower());
        assertEquals("Test2", old.getModel());
        assertEquals(newCar, list.get(1));
        assertEquals(999, list.get(1).getHorsepower());
        assertEquals("NewTest", list.get(1).getModel());
        assertEquals(3, list.size());
    }

    @Test
    void testIteration_afterSet_iterationWorksCorrectly() {
        Car newCar = new Car(777, "Replaced", 2024);
        list.set(1, newCar);
        int index = 0;
        for (Car car : list) {
            if (index == 0) {
                assertEquals(car1, car);
            } else if (index == 1) {
                assertEquals(newCar, car);
            } else if (index == 2) {
                assertEquals(car3, car);
            }
            index++;
        }
        assertEquals(3, index);
    }

    @Test
    void testSet_invalidIndex_throwsIndexOutOfBoundsException() {
        Car newCar = new Car(999, "NewTest", 2025);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set(10, newCar);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set(-1, newCar);
        });
    }

    @Test
    void testGet_invalidIndex_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(5);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(-1);
        });
    }

    @Test
    void testAdd_nullElement_returnsFalse() {
        CustomArrayList<Car> testList = new CustomArrayList<>();
        boolean result = testList.add(null);
        assertTrue(result);
        assertEquals(1, testList.size());
    }

    @Test
    void testClear_removeAllElements_collectionIsEmpty() {
        assertEquals(3, list.size());
        list.clear();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    void testToArray_convertToArray_returnsCorrectArray() {
        Object[] array = list.toArray();
        assertEquals(3, array.length);
        assertEquals(car1, array[0]);
        assertEquals(car2, array[1]);
        assertEquals(car3, array[2]);
    }

    @Test
    void testToString_returnsCorrectString() {
        String expected = "[" + car1 + ", " + car2 + ", " + car3 + "]";
        assertEquals(expected, list.toString());
    }

    @Test
    void testAdd_autoGrow_whenFull() {
        CustomArrayList<Car> testList = new CustomArrayList<>();
        for (int i = 0; i < 10; i++) {
            testList.add(new Car(i + 1, "Test", 2020));
        }
        assertEquals(10, testList.size(), "После добавления 10 элементов размер должен быть 10");
        Car car11 = new Car(999, "NewCar", 2025);
        testList.add(car11);
        assertEquals(11, testList.size(), "После добавления 11-го элемента размер должен быть 11");
        assertEquals(car11, testList.get(10), "11-й элемент должен быть на позиции 10");
    }

    @Test
    void testSet_replaceFirstElement_returnsOldElement() {
        Car newCar = new Car(999, "FirstReplaced", 2024);
        Car old = list.set(0, newCar);
        assertEquals(car1, old);
        assertEquals(newCar, list.get(0));
        assertEquals(car2, list.get(1));
        assertEquals(car3, list.get(2));
    }

    @Test
    void testSet_replaceLastElement_returnsOldElement() {
        Car newCar = new Car(999, "LastReplaced", 2024);
        Car old = list.set(2, newCar);
        assertEquals(car3, old);
        assertEquals(car1, list.get(0));
        assertEquals(car2, list.get(1));
        assertEquals(newCar, list.get(2));
    }

    @Test
    void testSet_afterClear_throwsException() {
        list.clear();
        Car newCar = new Car(999, "New", 2024);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set(0, newCar);
        });
    }

    @Test
    void testAdd_afterClear_addsToEmptyCollection() {
        list.clear();
        assertEquals(0, list.size());
        Car newCar = new Car(150, "AfterClear", 2020);
        list.add(newCar);
        assertEquals(1, list.size());
        assertEquals(newCar, list.get(0));
    }
}