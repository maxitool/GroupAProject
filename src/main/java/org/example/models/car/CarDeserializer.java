package org.example.models.car;

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
                System.out.println(++i + " argument of " + data +  " car class incorrectly set, correct format: key = value");
                return null;
            }
            dataHashMap.put(keyValue[0].trim(), keyValue[1].trim());
        }
        if (!isKeyContainsInMap(dataHashMap, "horsepower", data) ||
                !isKeyContainsInMap(dataHashMap, "model", data) ||
                !isKeyContainsInMap(dataHashMap, "year", data)) return null;
        try {
            return new Car(
                    Integer.parseInt(dataHashMap.get("horsepower").replace("hp","").trim()),
                    dataHashMap.get("model").replace("'","").replace("\"","").trim(),
                    Integer.parseInt(dataHashMap.get("year").trim()));
        } catch (NumberFormatException e) {
            System.out.println("Can't convert String data ( " + data + " ) to Car class, " + e.getMessage());
        }
        return null;
    }

    private static boolean isKeyContainsInMap(HashMap<String, String> hashMap, String key, String data) {
        boolean answer = hashMap.containsKey(key);
        if (!answer) System.out.println("Didn't receive " + key + " field for " + data + " Car class");
        return answer;
    }
}
