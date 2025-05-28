import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LambdaSortExample {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();

        // Adding sample names to the list
        names.add("Zara");
        names.add("Alex");
        names.add("John");
        names.add("Beth");

        System.out.println("Original list:");
        System.out.println(names);

        // Sorting the list using a lambda expression
        Collections.sort(names, (s1, s2) -> s1.compareTo(s2));

        System.out.println("\nSorted list:");
        System.out.println(names);
    }
}