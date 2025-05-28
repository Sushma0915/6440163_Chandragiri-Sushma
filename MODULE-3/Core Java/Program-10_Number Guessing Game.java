// Program 10: Number Guessing Game
import java.util.Scanner;
import java.util.Random;
public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        int numberToGuess = rand.nextInt(100) + 1;
        int guess = 0;
        while (guess != numberToGuess) {
            System.out.print("Guess a number between 1 and 100: ");
            guess = scanner.nextInt();
            if (guess < numberToGuess) System.out.println("Too low!");
            else if (guess > numberToGuess) System.out.println("Too high!");
        }
        System.out.println("Correct! The number was " + numberToGuess);
    }
}
