package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileService {

    public static List<Car> readCarsFromFile(String filename) {
        try {
            return Files.lines(Paths.get(filename))
                    .filter(line -> !line.trim().isEmpty())
                    .map(FileService::parseCar)
                    .filter(car -> car != null && car.getIsValidationGood())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void saveCarsToFile(List<Car> cars, String filename, boolean append) {
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
    private static Car parseCar(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        Car car = Car.stringToCar(line);

        if (car == null) {
            System.out.println("Не удалось распарсить: " + line);
            return null;
        }

        if (!car.getIsValidationGood()) {
            System.out.println("Невалидные данные: " + line);
            return null;
        }

        return car;
    }
}
