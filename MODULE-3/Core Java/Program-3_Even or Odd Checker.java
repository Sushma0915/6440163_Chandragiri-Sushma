// Program 3: Even or Odd Checker
import java.util.Scanner;
public class EvenOddChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an integer: ");
        int number = scanner.nextInt();
        if (number % 2 == 0)
            System.out.println("Even number");
        else
            System.out.println("Odd number");
    }
}
