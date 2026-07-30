package org.example;

import org.example.collections.CustomArrayList;

public interface FileOperationStrategy {
    void execute(String filename, CustomArrayList<Car> cars);
}

