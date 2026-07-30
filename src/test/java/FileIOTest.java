
import org.example.Car;
import org.example.FileServiceTxt;
import org.example.collections.CustomArrayList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileIOTest {

    private static final String TEST_FILE = "test_cars.txt";

    @Test
    public void testReadCarsFromFile() throws IOException {
        String testData = "Car{horsepower=300 hp, model='BMW M3', year=2020}\n" +
                "Car{horsepower=200 hp, model='Audi A4', year=2019}\n" +
                "Car{horsepower=250 hp, model='Mercedes C', year=2021}";
        Files.write(Paths.get(TEST_FILE), testData.getBytes());

        CustomArrayList<Car> cars = FileServiceTxt.readCarsFromFile(TEST_FILE);

        assertEquals(3, cars.size(), "Should read 3 cars");

        assertEquals("BMW M3", cars.get(0).getModel());
        assertEquals(300, cars.get(0).getHorsepower());
        assertEquals(2020, cars.get(0).getYear());

        assertEquals("Audi A4", cars.get(1).getModel());
        assertEquals(200, cars.get(1).getHorsepower());

        assertEquals("Mercedes C", cars.get(2).getModel());
        assertEquals(250, cars.get(2).getHorsepower());

        Files.deleteIfExists(Paths.get(TEST_FILE));
    }

    @Test
    public void testAppendMode() throws IOException {
        CustomArrayList<Car> firstCars = new CustomArrayList<>();
        firstCars.add(new Car(100, "Toyota", 2010));
        FileServiceTxt.saveCarsToFile(firstCars, TEST_FILE, false);

        List<String> linesAfterFirst = Files.readAllLines(Paths.get(TEST_FILE));
        assertEquals(1, linesAfterFirst.size());
        assertTrue(linesAfterFirst.get(0).contains("Toyota"));

        CustomArrayList<Car> secondCars = new CustomArrayList<>();
        secondCars.add(new Car(200, "Honda", 2015));
        FileServiceTxt.saveCarsToFile(secondCars, TEST_FILE, true);

        List<String> linesAfterSecond = Files.readAllLines(Paths.get(TEST_FILE));
        assertEquals(2, linesAfterSecond.size());

        assertTrue(linesAfterSecond.get(0).contains("Toyota"));
        assertTrue(linesAfterSecond.get(1).contains("Honda"));

        assertTrue(linesAfterSecond.get(0).contains("100"));
        assertTrue(linesAfterSecond.get(0).contains("2010"));

        assertTrue(linesAfterSecond.get(1).contains("200"));
        assertTrue(linesAfterSecond.get(1).contains("2015"));

        Files.deleteIfExists(Paths.get(TEST_FILE));
    }


    @Test
    public void testOverwriteMode() throws IOException {
        CustomArrayList<Car> firstCars = new CustomArrayList<>();
        firstCars.add(new Car(100, "Toyota", 2010));
        FileServiceTxt.saveCarsToFile(firstCars, TEST_FILE, false);

        List<String> linesAfterFirst = Files.readAllLines(Paths.get(TEST_FILE));
        assertEquals(1, linesAfterFirst.size());

        CustomArrayList<Car> secondCars = new CustomArrayList<>();
        secondCars.add(new Car(200, "Honda", 2015));
        FileServiceTxt.saveCarsToFile(secondCars, TEST_FILE, false);

        List<String> linesAfterSecond = Files.readAllLines(Paths.get(TEST_FILE));
        assertEquals(1, linesAfterSecond.size());
        assertTrue(linesAfterSecond.get(0).contains("Honda"));
        assertFalse(linesAfterSecond.get(0).contains("Toyota"));

        Files.deleteIfExists(Paths.get(TEST_FILE));
    }


    @Test
    public void testReadNonExistentFile() {
        CustomArrayList<Car> cars = FileServiceTxt.readCarsFromFile("non_existent_file.txt");

        assertNotNull(cars);
        assertTrue(cars.isEmpty());
    }


    @Test
    public void testSaveEmptyList() throws IOException {
        CustomArrayList<Car> emptyCars = new CustomArrayList<>();

        FileServiceTxt.saveCarsToFile(emptyCars, TEST_FILE, true);

        boolean fileExists = Files.exists(Paths.get(TEST_FILE));
        if (fileExists) {
            List<String> lines = Files.readAllLines(Paths.get(TEST_FILE));
            assertTrue(lines.isEmpty());
            Files.deleteIfExists(Paths.get(TEST_FILE));
        }
    }
}