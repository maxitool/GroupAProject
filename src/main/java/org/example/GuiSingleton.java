package org.example;

import org.example.console.readers.DataConsoleReader;
import org.example.sort.comparators.HorsepowerComparator;
import org.example.sort.comparators.ModelComparator;
import org.example.sort.comparators.YearComparator;
import org.example.console.readers.primitives.BooleanConsoleReader;
import org.example.console.readers.primitives.IntConsoleReader;
import org.example.console.readers.primitives.responses.BooleanResponse;
import org.example.console.readers.primitives.responses.IntResponse;
import org.example.console.readers.primitives.responses.StringResponse;
import org.example.console.readers.primitives.StringConsoleReader;
import org.example.collections.CustomArrayList;
import org.example.files.FileService;
import org.example.files.FileServiceStrategy;
import org.example.files.TxtFileService;
import org.example.models.car.Car;
import org.example.sort.BubbleSortStrategy;
import org.example.sort.EvenOddSortStrategy;
import java.nio.file.Path;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class GuiSingleton {
    public static final String GO_BACK_COMMAND = "back";
    private static final String GO_BACK_TO_MAIN_GUI = GO_BACK_COMMAND + ". Go to Main GUI.";
    private static final int CONSOLE_LINES_CAPACITY = 10000;

    private static final Path FILES_PATH = Path.of("data");
    private static final String DEFAULT_FILENAME = "sorted_cars.txt";

    private static final String MAIN_GUI = """
            Main GUI
            Choose a option:
            1. Fill data;
            2. Print data;
            3. Sort data;
            4. Write data to file;
            5. Count number of elements N in the collection;
            """ +
            GO_BACK_COMMAND + ". Stop program.";
    private static final HashMap<Integer, Runnable> MAIN_GUI_ACTIONS = new HashMap<>(Map.of(
            1, GuiSingleton::fillData,
            2, GuiSingleton::printData,
            3, GuiSingleton::sortData,
            4, GuiSingleton::writeDataToFile,
            5, GuiSingleton::countNumberOfElementsN
    ));

    private static final String FILL_DATA_GUI = """
            Fill data GUI
            Choose a option:
            1. Fill from the console;
            2. Fill from a file;
            3. Fill with generated data;
            """ +
            GO_BACK_TO_MAIN_GUI;
    private static final HashMap<Integer, Supplier<Boolean>> FILL_GUI_ACTIONS = new HashMap<>(Map.of(
            1, GuiSingleton::fillFromConsoleData,
            2, GuiSingleton::fillFromFileData,
            3, GuiSingleton::fillGeneratedData
    ));

    private static final String FILL_DATA_FROM_CONSOLE_GUI = """
            Fill data from console GUI
            """ +
            GO_BACK_COMMAND + ". Go to Fill data GUI.";

    private static final String FILL_DATA_FROM_FILE_GUI = """
            Fill data from file GUI
            What is the name of the file you want to read data from? +
            """ +
            GO_BACK_COMMAND + ". Go to Fill data GUI.";

    private static final String FILL_GENERATED_DATA_GUI = """
            Fill generated data GUI
            How much data do you need to generate?
            """ +
            GO_BACK_COMMAND + ". Go to Fill data GUI.";

    private static final String PRINT_DATA_GUI = """
            Print data GUI
            How much data do you need to output?
            """;

    private static final String SORT_DATA_GUI = """
            Sort data GUI
            Choose a option:
            1. Sorting by horsepower field;
            2. Sorting by model field;
            3. Sorting by year field;
            4. Sorting by even horsepower values;
            """ +
            GO_BACK_TO_MAIN_GUI;

    private static final String WRITE_DATA_TO_FILE_FILENAME_GUI = """
            Write data to file GUI
            """ +
            "Enter the file name or click Enter to use the default file name (" + DEFAULT_FILENAME + ").\n" +
            GO_BACK_TO_MAIN_GUI;

    private static final String BOOLEAN_ANSWER_TRUE = "yes", BOOLEAN_ANSWER_FALSE = "no";
    private static final String WRITE_DATA_TO_FILE_IS_REWRITE_GUI = """
            Write data to file GUI
            """ +
            "Clear the file before inserting data? (" + BOOLEAN_ANSWER_TRUE + '/' + BOOLEAN_ANSWER_FALSE + ")\n" +
            GO_BACK_TO_MAIN_GUI;

    private static final String COUNT_NUMBER_OF_ELEMENTS_GUI = """
            Write data to file GUI
            Count number of elements GUI
            Enter index of the N element you want to count
            """ +
            GO_BACK_TO_MAIN_GUI;

    private static final List<FileServiceStrategy> FILE_SERVICE_STRATEGIES_LIST = List.of(
            new TxtFileService()
    );

    private static List<Car> cars = new CustomArrayList<>();


    private GuiSingleton() {}

    public static GuiSingleton getInstance() { return Holder.instance; }

    public void run() {
        System.out.println("\nGroup A program is running.");
        IntResponse answer;
        do {
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
        } while (true);
    }

    private static void fillData() {
        IntResponse answer;
        do {
            System.out.println(FILL_DATA_GUI);
            do {
                answer = IntConsoleReader.getIntData();
            } while (answer.state != StringResponse.States.BACK_COMMAND && answer.state != StringResponse.States.OK);
            if (answer.state == StringResponse.States.BACK_COMMAND) {
                return;
            }
            if (!FILL_GUI_ACTIONS.containsKey(answer.intData)) {
                System.out.println("Can't recognize wrote option");
                continue;
            }
            if(FILL_GUI_ACTIONS.get(answer.intData).get()) {
                return;
            }
            System.out.println("The data wasn't filled in");
        } while (true);
    }

    private static boolean fillFromConsoleData() {
        System.out.println(FILL_DATA_FROM_CONSOLE_GUI);
        cars = DataConsoleReader.readCars();
        return true;
    }

    private static boolean fillFromFileData() {
        StringResponse answer;
        cars = Collections.unmodifiableList(cars);
        System.out.println(FILL_DATA_FROM_FILE_GUI);
        do {
            do {
                answer = StringConsoleReader.getStringData();
            } while (answer.state != StringResponse.States.BACK_COMMAND && answer.state != StringResponse.States.OK);
            if (answer.state == StringResponse.States.BACK_COMMAND) {
                return false;
            }
            String filename = answer.stringData;
            String streamFilename = filename;
            FileServiceStrategy strategy = FILE_SERVICE_STRATEGIES_LIST.stream()
                    .filter(item -> item.isFileFormatGood(streamFilename))
                    .findFirst().orElse(null);
            filename = FILES_PATH.resolve(answer.stringData).toString();
            if (strategy == null) {
                System.out.println("The file you wrote isn't in correct format.");
                System.out.println("Available formats:");
                FILE_SERVICE_STRATEGIES_LIST.forEach(item -> System.out.println(item.getFileFormat()));
                System.out.println("Please try again or enter 'back' to cancel:");
                continue;
            }
            cars = FileService.readCars(strategy, filename);
            return true;
        } while(true);
    }

    private static boolean fillGeneratedData() {
        IntResponse answer;
        System.out.println(FILL_GENERATED_DATA_GUI);
        do {
            answer = IntConsoleReader.getIntData();
        } while (answer.state != StringResponse.States.BACK_COMMAND && answer.state != StringResponse.States.OK);
        if (answer.state == StringResponse.States.BACK_COMMAND) {
            return false;
        }
        cars = DataGenerator.generateCars(answer.intData);
        return true;
    }

    private static void printData() {
        System.out.println(PRINT_DATA_GUI);
        System.out.println("Current size of the cars list = " + cars.size());
        System.out.println(GO_BACK_TO_MAIN_GUI);
        IntResponse answer;
        do {
            answer = IntConsoleReader.getIntData();
        } while (answer.state != StringResponse.States.BACK_COMMAND && answer.state != StringResponse.States.OK);
        if (answer.state == StringResponse.States.BACK_COMMAND) {
            return;
        }
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
        System.out.println("Press enter to continue");
        StringConsoleReader.getStringData();
    }

    private static void sortData() {
        IntResponse answer;
        BubbleSortStrategy bubbleSortStrategy;
        do {
            System.out.println(SORT_DATA_GUI);
            do {
                answer = IntConsoleReader.getIntData();
            } while (answer.state != StringResponse.States.BACK_COMMAND && answer.state != StringResponse.States.OK);
            if (answer.state == StringResponse.States.BACK_COMMAND) {
                return;
            }
            switch (answer.intData) {
                case 1:
                    cars = Collections.unmodifiableList(cars);
                    bubbleSortStrategy = new BubbleSortStrategy();
                    cars = bubbleSortStrategy.sort(cars, new HorsepowerComparator());
                    break;
                case 2:
                    bubbleSortStrategy = new BubbleSortStrategy();
                    cars = bubbleSortStrategy.sort(cars, new ModelComparator());
                    break;
                case 3:
                    bubbleSortStrategy = new BubbleSortStrategy();
                    cars = bubbleSortStrategy.sort(cars, new YearComparator());
                    break;
                case 4:
                    EvenOddSortStrategy evenOddSortStrategy = new EvenOddSortStrategy();
                    cars = evenOddSortStrategy.sort(cars, new HorsepowerComparator());
                    break;
                default:
                    System.out.println("Can't recognize wrote option");
            }
        } while (true);
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
        do {
            do {
                stringAnswer = StringConsoleReader.getStringData();
            } while (stringAnswer.state != StringResponse.States.BACK_COMMAND && stringAnswer.state != StringResponse.States.OK);
            if (stringAnswer.state == StringResponse.States.BACK_COMMAND) {
                return;
            }
            filename = stringAnswer.stringData;
            if (stringAnswer.stringData.isEmpty()) {
                filename = DEFAULT_FILENAME;
            }
            String streamFilename = filename;
            FileServiceStrategy strategy = FILE_SERVICE_STRATEGIES_LIST.stream()
                    .filter(item -> item.isFileFormatGood(streamFilename))
                    .findFirst().orElse(null);
            if (strategy == null) {
                System.out.println("The file you wrote isn't in correct format.");
                System.out.println("Available formats:");
                FILE_SERVICE_STRATEGIES_LIST.forEach(item -> System.out.println(item.getFileFormat()));
                System.out.println("Please try again (or enter 'back' to cancel):");
                continue;
            }
            filename = FILES_PATH.resolve(filename).toString();
            if (FileService.isFileExist(filename)) {
                System.out.println(WRITE_DATA_TO_FILE_IS_REWRITE_GUI);
                do {
                    booleanResponse = BooleanConsoleReader.getBooleanData(BOOLEAN_ANSWER_TRUE, BOOLEAN_ANSWER_FALSE);
                } while (booleanResponse.state != StringResponse.States.BACK_COMMAND && booleanResponse.state != StringResponse.States.OK);
                if (booleanResponse.state == StringResponse.States.BACK_COMMAND) {
                    return;
                }
                isRewrite = booleanResponse.booleanData;
            } else {
                System.out.println("The file will be created.");
            }
            FileService.writeCars(strategy, filename, !isRewrite, cars);
            break;
        } while (true);
    }

    private static void countNumberOfElementsN() {
        if (cars.isEmpty()) {
            System.out.println("The collection is empty.");
            return;
        }
        System.out.println(COUNT_NUMBER_OF_ELEMENTS_GUI);
        IntResponse intResponse;
        do {
            do {
                intResponse = IntConsoleReader.getIntData();
            } while (intResponse.state != StringResponse.States.BACK_COMMAND && intResponse.state != StringResponse.States.OK);
            if (intResponse.state == StringResponse.States.BACK_COMMAND) {
                return;
            }
            if (intResponse.intData < 0 || intResponse.intData >= cars.size()) {
                System.out.println("Car with this index doesn't exist!");
                continue;
            }
            Car target = cars.get(intResponse.intData);
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            System.out.print("Enter the number of threads (available: " + availableProcessors + "): ");
            do {
                intResponse = IntConsoleReader.getIntData();
            } while (intResponse.state != StringResponse.States.BACK_COMMAND && intResponse.state != StringResponse.States.OK);
            if (intResponse.state == StringResponse.States.BACK_COMMAND) {
                return;
            }
            CarCounter counter = new CarCounter(cars);
            counter.printOccurrences(target, intResponse.intData);
            return;
        } while (true);
    }

    private static class Holder {
        public static final GuiSingleton instance = new GuiSingleton();
    }
}
