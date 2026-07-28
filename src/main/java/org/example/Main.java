package org.example;

import org.example.ConsoleReaders.IntConsoleReader;
import org.example.ConsoleReaders.Responses.IntResponse;
import org.example.ConsoleReaders.Responses.StringResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;


public class Main {
    private static final String MAIN_GUI = "\nMain GUI\n" +
                                        "Choose a option:\n" +
                                        "1. Fill data;\n" +
                                        "2. Get data;\n" +
                                        "3. Sort data;\n" +
                                        "4. Write data to file;\n" +
                                        "back. Stop program.";

    private static final String FILL_DATA_GUI = "\nFill data GUI\n" +
                                                "Choose a option:\n" +
                                                "1. Fill from the console;\n" +
                                                "2. Fill from a file;\n" +
                                                "3. Fill with generated data;\n" +
                                                "back. Go to Main GUI.";

    private static final String GET_DATA_GUI = "\nGet data GUI\n" +
                                                "How much data do you need to output?\n" +
                                                "back. Go to Main GUI.";

    private static final HashMap<Integer, Runnable> MAIN_GUI_ACTIONS = new HashMap<>(Map.of(
            1, Main::fillData,
            2, Main::getData,
            3, Main::sortData,
            4, Main::writeDataToFile
    ));



    public static void main(String[] args) {
        // examples
        Example.exampleConsolePrintOneCar();
        //Car car = Example.exampleStringToCar();
        //Example.readDataFromConsole();

        Car car = new Car();
        System.out.println(car.getIsValidationGood());
        car.setHorsepower(1);
        System.out.println(car.getIsValidationGood());
        car.setModel("d");
        System.out.println(car.getIsValidationGood());
        car.setYear(1);
        System.out.println(car.getIsValidationGood());

        // program
        System.out.println("\nGroup A program is running.");
        IntResponse answer;
        while (true) {
            System.out.println(MAIN_GUI);
            do {
                answer = IntConsoleReader.getIntData();
            } while (answer.state != StringResponse.States.BACK_COMMAND && answer.state != StringResponse.States.OK);
            if (answer.state == StringResponse.States.BACK_COMMAND) {
                return;
            }
            if (!MAIN_GUI_ACTIONS.containsKey(answer.intData)) {
                System.out.println("Can't recognize wrote option");
                continue;
            }
            MAIN_GUI_ACTIONS.get(answer.intData).run();
        }
    }

    private static void fillData() {
        System.out.println(FILL_DATA_GUI);
        IntResponse answer;
        do {
            answer = IntConsoleReader.getIntData();
        } while (answer.state != StringResponse.States.BACK_COMMAND && answer.state != StringResponse.States.OK);
        if (answer.state == StringResponse.States.BACK_COMMAND) {
            return;
        }
        // answer.intData is option of FILL_DATA_GUI
        // To do with answer.intData like HashMap MAIN_GUI_ACTIONS
    }
    private static void getData() {
        System.out.println(GET_DATA_GUI);
        IntResponse answer;
        do {
            answer = IntConsoleReader.getIntData();
        } while (answer.state != StringResponse.States.BACK_COMMAND && answer.state != StringResponse.States.OK);
        if (answer.state == StringResponse.States.BACK_COMMAND) {
            return;
        }
        // answer.intData is how Car data get
        // To do with answer.intData
    }
    private static void sortData() {
    }
    private static void writeDataToFile() {
        List<Car> sortedCars = Example.getBMWCarsList(); // как обратиться
        if (sortedCars == null || sortedCars.isEmpty()) {
            System.out.println("Отсортированный список пуст! Сначала выполните сортировку.");
            return;
        }
        System.out.print("Введите имя файла (или Enter для sorted_cars.txt): ");
        String filename = IntConsoleReader.getStringData().stringData;
        if (filename.trim().isEmpty()) {
            filename = "sorted_cars.txt";
        }
        File file = new File(filename);
        boolean append = true;
        if (file.exists()) {
            System.out.print("Файл уже существует. Дописать (1) или перезаписать (2)? ");
            IntResponse answer = IntConsoleReader.getIntData();

            if (answer.state == StringResponse.States.OK && answer.intData == 2) {
                append = false;
                System.out.println("Файл будет перезаписан.");
            } else {
                append = true;
                System.out.println("Данные будут добавлены.");
            }
        } else {
            System.out.println("Файл будет создан.");
            append = false;
        }
        FileService.saveCarsToFile(sortedCars, filename, append);
    }
}
