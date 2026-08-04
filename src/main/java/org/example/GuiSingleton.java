package org.example;

import org.example.collections.CustomArrayList;
import org.example.console.readers.DataConsoleReader;
import org.example.console.readers.primitives.BooleanConsoleReader;
import org.example.console.readers.primitives.IntConsoleReader;
import org.example.console.readers.primitives.StringConsoleReader;
import org.example.console.readers.primitives.responses.BooleanResponse;
import org.example.console.readers.primitives.responses.IntResponse;
import org.example.console.readers.primitives.responses.StringResponse;
import org.example.files.FileService;
import org.example.files.FileServiceStrategy;
import org.example.files.TxtFileServiceStrategy;
import org.example.models.car.Car;
import org.example.sort.BubbleSortStrategy;
import org.example.sort.EvenOddSortStrategy;
import org.example.sort.SortStrategy;
import org.example.sort.comparators.HorsepowerComparator;
import org.example.sort.comparators.ModelComparator;
import org.example.sort.comparators.YearComparator;

import java.nio.file.Path;
import java.util.AbstractList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class GuiSingleton {
    public static final String GO_BACK_COMMAND = "back";
    private static final String GO_BACK_TO_MAIN_GUI = GO_BACK_COMMAND + ". Go to Main GUI";

    private static final int CONSOLE_LINES_CAPACITY = 10000;

    private static final Path FILES_PATH = Path.of("data");
    private static final String DEFAULT_FILENAME = "sorted_cars.txt";

    private static final String BOOLEAN_ANSWER_TRUE = "yes", BOOLEAN_ANSWER_FALSE = "no";

    private static final String MAIN_GUI = '\n' + """
            Main GUI
            Choose a option:
            1. Fill data;
            2. Print data;
            3. Sort data;
            4. Write data to file;
            5. Count number of elements N in the collection;
            6. Clear data;
            """ +
            GO_BACK_COMMAND + ". Stop program.";
    private static final Map<Integer, Runnable> MAIN_GUI_ACTIONS = Map.of(
            1, getInstance()::fillData,
            2, getInstance()::printData,
            3, getInstance()::sortData,
            4, getInstance()::writeDataToFile,
            5, getInstance()::countNumberOfElementsN,
            6, getInstance()::clearData
    );

    private static final String FILL_DATA_GUI = '\n' + """
            Fill data GUI
            Choose a option:
            1. Fill from the console;
            2. Fill from a file;
            3. Fill with generated data;
            """ +
            GO_BACK_TO_MAIN_GUI;
    private static final Map<Integer, Supplier<Boolean>> FILL_GUI_ACTIONS = Map.of(
            1, getInstance()::fillFromConsoleData,
            2, getInstance()::fillFromFileData,
            3, getInstance()::fillGeneratedData
    );

    private static final String FILL_DATA_FROM_CONSOLE_GUI = '\n' + """
            Fill data from console GUI
            """ +
            GO_BACK_COMMAND + ". Go to Fill data GUI";

    private static final String FILL_DATA_FROM_FILE_GUI = '\n' + """
            Fill data from file GUI
            What is the name of the file you want to read data from?
            """ +
            GO_BACK_COMMAND + ". Go to Fill data GUI";

    private static final String FILL_GENERATED_DATA_GUI = '\n' + """
            Fill generated data GUI
            How much data do you need to generate?
            """ +
            GO_BACK_COMMAND + ". Go to Fill data GUI";

    private static final String PRINT_DATA_GUI = '\n' + """
            Print data GUI
            How much data do you need to output?""";

    private static final String SORT_DATA_GUI = '\n' + """
            Sort data GUI
            Choose a option:
            1. Sorting by horsepower field;
            2. Sorting by model field;
            3. Sorting by year field;
            4. Sorting by even horsepower values;
            """ +
            GO_BACK_TO_MAIN_GUI;
    private static final SortStrategy bubbleSortStrategy = new BubbleSortStrategy();
    private static final Map<Integer, SortStrategyAndComparator> SORT_GUI_ACTIONS = Map.of(
            1, new SortStrategyAndComparator(bubbleSortStrategy, new HorsepowerComparator()),
            2, new SortStrategyAndComparator(bubbleSortStrategy, new ModelComparator()),
            3, new SortStrategyAndComparator(bubbleSortStrategy, new YearComparator()),
            4, new SortStrategyAndComparator(new EvenOddSortStrategy(), new HorsepowerComparator())
    );
    private static final String DO_PRINT_AFTER_SORT_GUI =
            "Print the resulting data after sorting? (" + BOOLEAN_ANSWER_TRUE + '/' + BOOLEAN_ANSWER_FALSE + ')';

    private static final String WRITE_DATA_TO_FILE_FILENAME_GUI = '\n' + """
            Write data to file GUI
            """ +
            "Enter the file name or click Enter to use the default file name (" + DEFAULT_FILENAME + ").\n" +
            GO_BACK_TO_MAIN_GUI;
    private static final String WRITE_DATA_TO_FILE_IS_REWRITE_GUI =
            "Clear the file before inserting data? (" + BOOLEAN_ANSWER_TRUE + '/' + BOOLEAN_ANSWER_FALSE + ")\n" +
                    GO_BACK_TO_MAIN_GUI;

    private static final String COUNT_NUMBER_OF_ELEMENTS_GUI = '\n' + """
            Count number of elements GUI
            Enter index of the N element you want to count""";

    private static final List<FileServiceStrategy> FILE_SERVICE_STRATEGIES_LIST = List.of(
            new TxtFileServiceStrategy()
    );

    private static final Supplier<AbstractList<Car>> CREATE_COLLECTION_SUPPLIER = CustomArrayList::new;
    private static List<Car> cars = CREATE_COLLECTION_SUPPLIER.get();


    private GuiSingleton() {
    }

    public static GuiSingleton getInstance() {
        return Holder.instance;
    }

    public void run() {
        System.out.println("\nGroup A program is running");
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

    private void fillData() {
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
            if (FILL_GUI_ACTIONS.get(answer.intData).get()) {
                return;
            }
        } while (true);
    }

    private boolean fillFromConsoleData() {
        List<Car> newCars;
        System.out.println(FILL_DATA_FROM_CONSOLE_GUI);
        newCars = DataConsoleReader.readCars(CREATE_COLLECTION_SUPPLIER);
        if (newCars.isEmpty()) {
            return false;
        }
        cars = newCars;
        return true;
    }

    private boolean fillFromFileData() {
        List<Car> newCars;
        StringResponse answer;
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
                System.out.println("The file you wrote isn't in correct format");
                System.out.println("Available formats:");
                FILE_SERVICE_STRATEGIES_LIST.forEach(item -> System.out.println(item.getFileFormat()));
                System.out.println("Please try again or enter 'back' to cancel");
                continue;
            }
            newCars = FileService.readCars(strategy, filename, CREATE_COLLECTION_SUPPLIER);
            if (newCars.isEmpty()) {
                return false;
            }
            cars = newCars;
            System.out.println(cars.size() + " cars was received");
            return true;
        } while (true);
    }

    private boolean fillGeneratedData() {
        IntResponse answer;
        System.out.println(FILL_GENERATED_DATA_GUI);
        do {
            answer = IntConsoleReader.getIntData();
        } while (answer.state != StringResponse.States.BACK_COMMAND && answer.state != StringResponse.States.OK);
        if (answer.state == StringResponse.States.BACK_COMMAND) {
            return false;
        }
        if (answer.intData <= 0) {
            System.out.println("The size of data must be greater than 0");
            return false;
        }
        cars = DataGenerator.generateCars(answer.intData, CREATE_COLLECTION_SUPPLIER);
        return true;
    }

    private void printData() {
        if (cars.isEmpty()) {
            System.out.println("The data is empty");
            return;
        }
        System.out.println(PRINT_DATA_GUI);
        System.out.println("Current size of the data list = " + cars.size());
        System.out.println(GO_BACK_TO_MAIN_GUI);
        IntResponse answer;
        do {
            answer = IntConsoleReader.getIntData();
        } while (answer.state != StringResponse.States.BACK_COMMAND && answer.state != StringResponse.States.OK);
        if (answer.state == StringResponse.States.BACK_COMMAND) {
            return;
        }
        if (answer.intData < 0) {
            System.out.println("The wrote number must be greater then or equal to 0");
            return;
        }
        int count = answer.intData;
        if (count > cars.size()) {
            System.out.println("Number of elements is less than the wrote value, " + cars.size() + " elements will be printed\n");
            count = cars.size();
        }
        if (count > CONSOLE_LINES_CAPACITY) {
            System.out.println("The wrote value must be less than console lines capacity, " + CONSOLE_LINES_CAPACITY + " elements will be printed\n");
            count = CONSOLE_LINES_CAPACITY;
        }
        cars.stream().limit(count).forEach(item -> System.out.println(item.toString()));
        System.out.println("Press enter to continue");
        StringConsoleReader.getStringData();
    }

    private void sortData() {
        if (cars.isEmpty()) {
            System.out.println("The list of data is empty");
            return;
        }
        IntResponse intAnswer;
        BooleanResponse booleanAnswer;
        do {
            System.out.println(SORT_DATA_GUI);
            do {
                intAnswer = IntConsoleReader.getIntData();
            } while (intAnswer.state != StringResponse.States.BACK_COMMAND && intAnswer.state != StringResponse.States.OK);
            if (intAnswer.state == StringResponse.States.BACK_COMMAND) {
                return;
            }
            if (!SORT_GUI_ACTIONS.containsKey(intAnswer.intData)) {
                System.out.println("Can't recognize wrote option");
                continue;
            }
            if (SORT_GUI_ACTIONS.get(intAnswer.intData).strategy == null || SORT_GUI_ACTIONS.get(intAnswer.intData).comparator == null) {
                System.out.println("Sort strategy or comparator is null in SORT_GUI_ACTIONS");
                return;
            }
            cars = SORT_GUI_ACTIONS.get(intAnswer.intData).strategy.sort(cars, SORT_GUI_ACTIONS.get(intAnswer.intData).comparator);
            System.out.println(DO_PRINT_AFTER_SORT_GUI);
            do {
                booleanAnswer = BooleanConsoleReader.getBooleanData(BOOLEAN_ANSWER_TRUE, BOOLEAN_ANSWER_FALSE);
            } while (booleanAnswer.state != StringResponse.States.BACK_COMMAND && booleanAnswer.state != StringResponse.States.OK);
            if (booleanAnswer.state == StringResponse.States.BACK_COMMAND) {
                return;
            }
            if (booleanAnswer.booleanData) {
                printData();
            }
        } while (true);
    }

    private void writeDataToFile() {
        StringResponse stringAnswer;
        BooleanResponse booleanResponse;
        if (cars == null || cars.isEmpty()) {
            System.out.println("The list of data is empty");
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

    private void countNumberOfElementsN() {
        if (cars.isEmpty()) {
            System.out.println("The list of data is empty");
            return;
        }
        System.out.println(COUNT_NUMBER_OF_ELEMENTS_GUI);
        System.out.println("Indexes from 0 to " + (cars.size() - 1));
        System.out.println(GO_BACK_TO_MAIN_GUI);
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
            System.out.println("Selected car: " + target.toString());
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            System.out.println("Enter count of threads (available: " + availableProcessors + "): ");
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

    private void clearData() {
        cars.clear();
        System.out.println("The data has been cleared");
    }

    private static class Holder {
        public static final GuiSingleton instance = new GuiSingleton();
    }


    private static class SortStrategyAndComparator {
        public final SortStrategy strategy;
        public final Comparator<Car> comparator;

        public SortStrategyAndComparator(SortStrategy strategy, Comparator<Car> comparator) {
            this.strategy = strategy;
            this.comparator = comparator;
        }
    }
}
