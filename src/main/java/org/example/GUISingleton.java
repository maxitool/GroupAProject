package org.example;

import org.example.ConsoleReaders.IntConsoleReader;
import org.example.ConsoleReaders.Responses.IntResponse;
import org.example.ConsoleReaders.Responses.StringResponse;

import java.util.HashMap;
import java.util.Map;

public class GUISingleton {
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
            1, GUISingleton::fillData,
            2, GUISingleton::getData,
            3, GUISingleton::sortData,
            4, GUISingleton::writeDataToFile
    ));
    private static final HashMap<Integer, Runnable> FILL_GUI_ACTIONS = new HashMap<>(Map.of(
    ));


    private GUISingleton() {}

    public static GUISingleton getInstance() { return Holder.instance; }

    public void run() {
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
        while (true) {
            System.out.println(FILL_DATA_GUI);
            IntResponse answer;
            do {
                answer = IntConsoleReader.getIntData();
            } while (answer.state != StringResponse.States.BACK_COMMAND && answer.state != StringResponse.States.OK);
            if (answer.state == StringResponse.States.BACK_COMMAND) return;
            if (!FILL_GUI_ACTIONS.containsKey(answer.intData)) {
                System.out.println("Can't recognize wrote option");
                continue;
            }
            FILL_GUI_ACTIONS.get((answer.intData)).run();
            return;
        }
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
    }


    private static class Holder {
        public static final GUISingleton instance = new GUISingleton();
    }
}
