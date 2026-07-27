import org.example.Car;
import org.example.CarFiller;
import org.example.collections.CustomArrayList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StreamFillTest {
    public static void main(String[] args) {
        System.out.println("Random fill (5)");
        CustomArrayList<Car> randomCars = CarFiller.fillRandom(5);
        System.out.println("Size: " + randomCars.size());
        for (Car c : randomCars){
            System.out.println(c);
        }

        System.out.println("\nFill from file");
        CustomArrayList<Car> fileCars = CarFiller.fillFromFile("data/cars.txt");
        System.out.println("From file size: " + fileCars.size());
        for (Car c : fileCars){
            System.out.println(c);
        }

        // Подменяем System.in на тестовые данные
//        String input = "2\n150, Toyota, 2020\n200, BMW, 2021\n";
//        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//
//        // Теперь программа думает, что пользователь ввёл эти данные
//        CustomArrayList<Car> cars = CarFiller.fillFromConsole();
//
//        // Проверяем результат
//        assert cars.size() == 2;
//        assert cars.get(0).getHorsepower() == 150;
//        assert cars.get(1).getHorsepower() == 200;
//
//        System.out.println("✅ Console fill test passed with mock input!");
        System.out.println("=== Test: Console Fill with Mock ===");

        // 1. Готовим тестовые данные
        //    Это именно то, что пользователь ввёл бы с клавиатуры
        String simulatedInput = "2\n" +              // Количество машин
                "150, Toyota, 2020\n" + // Машина #1
                "200, BMW, 2021\n";     // Машина #2

        System.out.println("Simulated input:");
        System.out.println("  " + simulatedInput.replace("\n", "\n  "));

        // 2. Превращаем строку в InputStream
        byte[] bytes = simulatedInput.getBytes();
        ByteArrayInputStream mockInput = new ByteArrayInputStream(bytes);

        // 3. Запоминаем оригинальный System.in (чтобы потом восстановить)
        InputStream originalIn = System.in;

        try {
            // 4. Подменяем System.in на наш тестовый поток
            System.setIn(mockInput);

            // 5. Вызываем метод, который использует System.in
            CustomArrayList<Car> cars = CarFiller.fillFromConsole();

            // 6. Проверяем результат
            System.out.println("\n=== Results ===");
            System.out.println("Cars count: " + cars.size());
            System.out.println("Car #1: " + cars.get(0));
            System.out.println("Car #2: " + cars.get(1));

            if (cars.size() == 2 &&
                    cars.get(0).getHorsepower() == 150 &&
                    cars.get(1).getHorsepower() == 200) {
                System.out.println("✅ Test passed!");
            } else {
                System.out.println("❌ Test failed!");
            }

        } finally {
            // 7. Восстанавливаем оригинальный System.in
            //    Это важно, чтобы не сломать другие тесты!
            System.setIn(originalIn);
        }
    }
}

