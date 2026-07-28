package org.example;

import java.util.HashMap;

public class CarDeserializer {
    public static Car stringToCar(String data) {
        if (data == null) {
            System.out.println("String data is null");
            return null;
        }
        String dataWithoutCar = data.replace("Car", "").replace("{","").replace("}","").trim();
        String[] dataArray = dataWithoutCar.split(",");
        HashMap<String, String> dataHashMap = new HashMap<>();
        String[] keyValue;
        for (int i = 0; i < dataArray.length; i++) {
            keyValue = dataArray[i].split("=");
            if (keyValue.length != 2) {
                System.out.println(i + " argument of " + data +  " car class incorrectly set, correct format: key = value");
                return null;
            }
            dataHashMap.put(keyValue[0], keyValue[1]);
        }
        if (!dataHashMap.containsKey("horsepower")) {
            System.out.println("Didn't receive horsepower field for " + data + " Car class");
            return null;
        }
        if (!dataHashMap.containsKey("model")) {
            System.out.println("Didn't receive model field for " + data + " Car class");
            return null;
        }
        if (!dataHashMap.containsKey("year")) {
            System.out.println("Didn't receive year field for " + data + " Car class");
            return null;
        }
        try {
            return new Car(
                    Integer.parseInt(dataHashMap.get("horsepower").replace("hp","").trim()),
                    dataHashMap.get("model").replace("'","").trim(),
                    Integer.parseInt(dataHashMap.get("year").trim()));
        } catch (NumberFormatException e) {
            System.out.println("Can't convert String data ( " + data + " ) to Car class, reason: " + e.getMessage());
        }
        return null;
    }
}
