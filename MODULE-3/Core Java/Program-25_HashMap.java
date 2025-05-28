import java.util.HashMap;
import java.util.Scanner;

public class HashMapExample {
    public static void main(String[] args) {
        HashMap<Integer, String> studentMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter student ID and name pairs (type -1 to stop):");

        while (true) {
            System.out.print("Enter student ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (id == -1) {
                break;
            }

            System.out.print("Enter student name: ");
            String name = scanner.nextLine();

            studentMap.put(id, name);
        }

        System.out.print("\nEnter a student ID to retrieve the name: ");
        int searchId = scanner.nextInt();

        if (studentMap.containsKey(searchId)) {
            System.out.println("Student name: " + studentMap.get(searchId));
        } else {
            System.out.println("No student found with ID " + searchId);
        }

        scanner.close();
    }
}