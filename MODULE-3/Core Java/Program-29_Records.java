import java.util.List;
import java.util.stream.Collectors;

// Define a record named Person
record Person(String name, int age) {}

public class RecordExample {
    public static void main(String[] args) {
        // Create instances of Person
        Person p1 = new Person("Alice", 30);
        Person p2 = new Person("Bob", 22);
        Person p3 = new Person("Charlie", 17);
        Person p4 = new Person("Diana", 25);

        // Print the person records
        System.out.println("All Persons:");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);

        // Store records in a List
        List<Person> people = List.of(p1, p2, p3, p4);

        // Filter people aged 18 and above using Streams
        List<Person> adults = people.stream()
                                    .filter(p -> p.age() >= 18)
                                    .collect(Collectors.toList());

        System.out.println("\nAdults (age 18+):");
        adults.forEach(System.out::println);
    }
}