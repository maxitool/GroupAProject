package org.example;

import org.example.comparator.HorsepowerComparator;
import org.example.comparator.ModelComparator;
import org.example.comparator.YearComparator;
import org.example.console.readers.BooleanConsoleReader;
import org.example.console.readers.IntConsoleReader;
import org.example.console.readers.responses.BooleanResponse;
import org.example.console.readers.responses.IntResponse;
import org.example.console.readers.responses.StringResponse;
import org.example.console.readers.StringConsoleReader;
import org.example.collections.CustomArrayList;
import org.example.models.car.Car;
import org.example.sort.BubbleSortStrategy;
import org.example.sort.EvenOddSortStrategy;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GUISingleton {
    private static final int CONSOLE_LINES_CAPACITY = 10000;
    private static final String FILES_PATH = "data\\";
    private static final String DEFAULT_FILENAME = "sorted_cars.txt";

    private static final String MAIN_GUI = "\nMain GUI\n" +
            "Choose a option:\n" +
            "1. Fill data;\n" +
            "2. Print data;\n" +
            "3. Sort data;\n" +
            "4. Write data to file;\n" +
            "5. Count number of elements N in the collection;\n" +
            "back. Stop program.";

    private static final String FILL_DATA_GUI = "\nFill data GUI\n" +
            "Choose a option:\n" +
            "1. Fill from the console;\n" +
            "2. Fill from a file;\n" +
            "3. Fill with generated data;\n" +
            "back. Go to Main GUI.";

    private static final String FILL_DATA_FROM_CONSOLE_GUI = "\nFill data from console GUI\n" +
            "back. Go to Fill data GUI.";

    private static final String FILL_DATA_FROM_FILE_GUI = "\nFill data from file GUI\n" +
            "What is the name of the file you want to read data from?\n" +
            "back. Go to Fill data GUI.";

    private static final String FILL_GENERATED_DATA_GUI = "\nFill generated data GUI\n" +
            "How much data do you need to generate?\n" +
            "back. Go to Fill data GUI.";

    private static final String GET_DATA_GUI = "\nPrint data GUI\n" +
            "How much data do you need to output?\n" +
            "back. Go to Main GUI.";

    private static final String SORT_DATA_GUI = "\nSort data GUI\n" +
            "Choose a option:\n" +
            "1. Sorting by horsepower field;\n" +
            "2. Sorting by model field;\n" +
            "3. Sorting by year field;\n" +
            "4. Sorting by even horsepower values;\n" +
            "back. Go to Main GUI.";

    private static final String WRITE_DATA_TO_FILE_FILENAME_GUI = "\nWrite data to file GUI\n" +
            "Enter the file name or click Enter to use the default file name (" + DEFAULT_FILENAME + ").\n" +
            "back. Go to Main GUI.";

    private static final String BOOLEAN_ANSWER_TRUE = "yes", BOOLEAN_ANSWER_FALSE = "no";

    private static final String WRITE_DATA_TO_FILE_IS_REWRITE_GUI = "\nWrite data to file GUI\n" +
            "Clear the file before inserting data? (" + BOOLEAN_ANSWER_TRUE + '/' + BOOLEAN_ANSWER_FALSE + ")\n" +
            "back. Go to Main GUI.";

    private static final String COUNT_NUMBER_OF_ELEMENTS_GUI = "\nCount number of elements GUI\n" +
            "Enter index of the N element you want to count\n" +
            "back. Go to Main GUI.";

    private static final HashMap<Integer, Runnable> MAIN_GUI_ACTIONS = new HashMap<>(Map.of(
            1, GUISingleton::fillData,
            2, GUISingleton::printData,
            3, GUISingleton::sortData,
            4, GUISingleton::writeDataToFile,

            5,GUISingleton::countNumberOfElementsN


    ));
    private static final HashMap<Integer, Supplier<Boolean>> FILL_GUI_ACTIONS = new HashMap<>(Map.of(
            1, GUISingleton::fillFromConsoleData,
            2, GUISingleton::fillFromFileData,
            3, GUISingleton::fillGeneratedData
    ));

    private static CustomArrayList<Car> cars = new CustomArrayList<>();


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
        IntResponse answer;
        while (true) {
            System.out.println(FILL_DATA_GUI);
            do {
                answer = IntConsoleReader.getIntData();
            } while (answer.state != StringResponse.States.BACK_COMMAND && answer.state != StringResponse.States.OK);
            if (answer.state == StringResponse.States.BACK_COMMAND) return;
            if (!FILL_GUI_ACTIONS.containsKey(answer.intData)) {
                System.out.println("Can't recognize wrote option");
                continue;
            }
            if(FILL_GUI_ACTIONS.get(answer.intData).get()) return;
        }
    }

    private static boolean fillFromConsoleData() {
        System.out.println(FILL_DATA_FROM_CONSOLE_GUI);
        cars = CarFiller.fillFromConsole();
        return true;
    }

    private static boolean fillFromFileData() {
        StringResponse answer;
        System.out.println(FILL_DATA_FROM_FILE_GUI);
        while(true) {
            do {
                answer = StringConsoleReader.getStringData();
            } while (answer.state != StringResponse.States.BACK_COMMAND && answer.state != StringResponse.States.OK);
            if (answer.state == StringResponse.States.BACK_COMMAND) return false;
            answer.stringData = FILES_PATH + answer.stringData;
            cars = CarFiller.fillFromFile(answer.stringData);
            return true;
        }
    }

    private static boolean fillGeneratedData() {
        IntResponse answer;
        System.out.println(FILL_GENERATED_DATA_GUI);
        do {
            answer = IntConsoleReader.getIntData();
        } while (answer.state != StringResponse.States.BACK_COMMAND && answer.state != StringResponse.States.OK);
        if (answer.state == StringResponse.States.BACK_COMMAND) return false;
        cars = CarFiller.fillRandom(answer.intData);
        return true;
    }

    private static void printData() {
        System.out.println(GET_DATA_GUI);
        IntResponse answer;
        do {
            answer = IntConsoleReader.getIntData();
        } while (answer.state != StringResponse.States.BACK_COMMAND && answer.state != StringResponse.States.OK);
        if (answer.state == StringResponse.States.BACK_COMMAND) return;
        int count = answer.intData;
        if (count > cars.size()) {
            System.out.println("Number of cars is less than the wrote value, " + cars.size() + " cars will be printed\n");
            count = cars.size();
        }
        if (count > CONSOLE_LINES_CAPACITY) {
            System.out.println("The wrote value must be less than console lines capacity, " + CONSOLE_LINES_CAPACITY + " cars will be printed\n");
            count = CONSOLE_LINES_CAPACITY;
        }
        cars.stream().limit(count).forEach(item -> System.out.println(item.toString()));
    }

    private static void sortData() {
        IntResponse answer;
        BubbleSortStrategy bubbleSortStrategy;
        while (true) {
            System.out.println(SORT_DATA_GUI);
            do {
                answer = IntConsoleReader.getIntData();
            } while (answer.state != StringResponse.States.BACK_COMMAND && answer.state != StringResponse.States.OK);
            if (answer.state == StringResponse.States.BACK_COMMAND) return;
            switch (answer.intData) {
                case 1:
                    bubbleSortStrategy = new BubbleSortStrategy();
                    bubbleSortStrategy.sort(cars, new HorsepowerComparator());
                    break;
                case 2:
                    bubbleSortStrategy = new BubbleSortStrategy();
                    bubbleSortStrategy.sort(cars, new ModelComparator());
                    break;
                case 3:
                    bubbleSortStrategy = new BubbleSortStrategy();
                    bubbleSortStrategy.sort(cars, new YearComparator());
                    break;
                case 4:
                    EvenOddSortStrategy evenOddSortStrategy = new EvenOddSortStrategy();
                    evenOddSortStrategy.sort(cars, new HorsepowerComparator());
                    break;
                default:
                    System.out.println("Can't recognize wrote option");
            }
        }
    }

    private static void writeDataToFile() {
        StringResponse stringAnswer; BooleanResponse booleanResponse;
        if (cars == null || cars.isEmpty()) {
            System.out.println("The car list is empty. Please sort the data first.");
            return;
        }
        System.out.println(WRITE_DATA_TO_FILE_FILENAME_GUI);
        String filename;
        boolean isRewrite = false;
        while (true) {
            do {
                stringAnswer = StringConsoleReader.getStringData();
            } while (stringAnswer.state != StringResponse.States.BACK_COMMAND && stringAnswer.state != StringResponse.States.OK);
            if (stringAnswer.state == StringResponse.States.BACK_COMMAND) return;
            filename = stringAnswer.stringData;
            if (stringAnswer.stringData.isEmpty()) filename = DEFAULT_FILENAME;
            filename = FILES_PATH + filename;
            File file = new File(filename);
            if (!filename.toLowerCase().endsWith(".txt")) {
                System.out.println("Error! The file name must end with .txt");
                System.out.println("Please try again (or enter 'back' to cancel):");
                continue;
            }
            if (file.exists()) {
                System.out.println(WRITE_DATA_TO_FILE_IS_REWRITE_GUI);
                do {
                    booleanResponse = BooleanConsoleReader.getBooleanData(BOOLEAN_ANSWER_TRUE, BOOLEAN_ANSWER_FALSE);
                } while (booleanResponse.state != StringResponse.States.BACK_COMMAND && booleanResponse.state != StringResponse.States.OK);
                if (booleanResponse.state == StringResponse.States.BACK_COMMAND) return;
                isRewrite = booleanResponse.booleanData;
            } else {
                System.out.println("Файл будет создан.");
            }
            FileServiceTxt.saveCarsToFile(cars, filename , !isRewrite);
        }
    }

    private static void countNumberOfElementsN() {
        if (cars.isEmpty()) {
            System.out.println("The collection is empty.");
            return;
        }
        if (cars.size() == 1) {
            System.out.println("The collection size = 1.");
            return;
        }
        System.out.println(COUNT_NUMBER_OF_ELEMENTS_GUI);
        IntResponse intResponse;
        while (true) {
            do {
                intResponse = IntConsoleReader.getIntData();
            } while (intResponse.state != StringResponse.States.BACK_COMMAND && intResponse.state != StringResponse.States.OK);
            if (intResponse.state == StringResponse.States.BACK_COMMAND) return;
            if (intResponse.intData < 0 || intResponse.intData >= cars.size()) {
                System.out.println("Car with this index does not exist!");
                continue;
            }
            Car target = cars.get(intResponse.intData);
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            System.out.print("Enter the number of threads (available: " + availableProcessors + "): ");
            do {
                intResponse = IntConsoleReader.getIntData();
            } while (intResponse.state != StringResponse.States.BACK_COMMAND && intResponse.state != StringResponse.States.OK);
            if (intResponse.state == StringResponse.States.BACK_COMMAND) return;
            CarCounter counter = new CarCounter(cars);
            counter.printOccurrences(target, intResponse.intData);
            return;
        }
    }

    private static class Holder {
        public static final GUISingleton instance = new GUISingleton();
    }
}
