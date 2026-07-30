package org.example;

import org.example.collections.CustomArrayList;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileService {

    public static CustomArrayList<Car> readCarsFromFile(String filename) {
        try {
            return Files.lines(Paths.get(filename))
                    .filter(line -> !line.trim().isEmpty())
                    .map(CarDeserializer::stringToCar)
                    .filter(CarValidator::validateCar)
                    .collect(Collectors.collectingAndThen(Collectors.toList(),CustomArrayList::new));
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return new CustomArrayList<>();
        }
    }

    public static void saveCarsToFile(CustomArrayList<Car> cars, String filename, boolean append) {
        if (cars == null || cars.isEmpty()) {
            System.out.println("Нет данных для записи!");
            return;
        }

        try (FileWriter writer = new FileWriter(filename, append)) {
            for (Car car : cars) {
                writer.write(car.toString() + "\n");
            }
            System.out.println("Данные сохранены в " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}
