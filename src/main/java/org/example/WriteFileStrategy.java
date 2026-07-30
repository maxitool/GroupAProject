package org.example;

import org.example.collections.CustomArrayList;
import org.example.models.car.Car;

import java.io.FileWriter;
import java.io.IOException;

public class WriteFileStrategy implements FileOperationStrategy{
    private final boolean append;

    public WriteFileStrategy(boolean append) {
        this.append = append;
    }

    @Override
    public void execute(String filename, CustomArrayList<Car> cars) {
        if (cars == null || cars.isEmpty()) {
            System.out.println("No data to write!");
            return;
        }

        try (FileWriter writer = new FileWriter(filename, append)) {
            for (Car car : cars) {
                writer.write(car.toString() + "\n");
            }
            System.out.println("Data saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

    }
}
