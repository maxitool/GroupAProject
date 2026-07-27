package org.example;

public class CarDeserializer {
    public static Car stringToCar(String data) {
        if (data == null) {
            System.out.println("String data is null");
            return null;
        }
        String dataWithoutCar = data.replace("Car", "").replace("{","").replace("}","").trim();
        String[] dataArray = dataWithoutCar.split(",");
        if (dataArray.length != Car.COUNT_FIELDS) {
            System.out.println("Count fields in Car class must be " + Car.COUNT_FIELDS + ", the received data have " + dataArray.length + " fields");
            return null;
        }
        try {
            return new Car(
                    Integer.parseInt(dataArray[0].replace("horsepower","").replace("=","").replace("hp","").trim()),
                    dataArray[1].replace("model","").replace("=","").replace("'","").trim(),
                    Integer.parseInt(dataArray[2].replace("year","").replace("=","").trim()));
        } catch (NumberFormatException e) {
            System.out.println("Can't convert String data ( " + data + " ) to Car class, reason: " + e.getMessage());
        }
        return null;
    }
}
