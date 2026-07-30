package org.example;
import org.example.collections.CustomArrayList;

public class FileContext {
    private FileOperationStrategy strategy;

    public void setStrategy(FileOperationStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeOperation(String filename, CustomArrayList<Car> cars) {
        if (strategy == null) {
            System.out.println("Strategy not selected!");
            return;
        }
        strategy.execute(filename, cars);
    }
}