package org.example;

import org.example.ConsoleReaders.IntConsoleReader;
import org.example.ConsoleReaders.Responses.IntResponse;
import org.example.ConsoleReaders.Responses.StringResponse;
import org.example.collections.CustomArrayList;

import java.util.stream.IntStream;

public class CarFiller {
    public static CustomArrayList<Car> fillFromConsole(){
        System.out.println("Enter number of cars: ");
        IntResponse countResponse = IntConsoleReader.getIntData();
        if (countResponse.state != StringResponse.States.OK){
            System.out.println("Invalid count, returning empty list.");
            return new CustomArrayList<>();
        }
        int count = countResponse.intData;

        CustomArrayList<Car> cars = new CustomArrayList<>();
    }
}
