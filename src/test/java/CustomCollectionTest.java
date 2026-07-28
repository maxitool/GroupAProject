import org.example.Car;
import org.example.collections.CustomArrayList;

public class CustomCollectionTest {
    public static void main(String[] args) {
        CustomArrayList<Car> list = new CustomArrayList<>();
        System.out.println("Initial size: " + list.size()); // 0

        Car car1 = new Car(100, "Test1", 2020);
        Car car2 = new Car(200, "Test2", 2021);
        list.add(car1);
        list.add(car2);
        System.out.println("Size after add: " + list.size()); // 2
        System.out.println("Element at 0: " + list.get(0));
        System.out.println("Element at 1: " + list.get(1));

        for(Car c : list){
            System.out.println("\nTest iteration: "+ c);
        }

        try {
            list.get(5);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("\nIndexOutOfBoundsException caught: " + e.getMessage());
        }

    }
}