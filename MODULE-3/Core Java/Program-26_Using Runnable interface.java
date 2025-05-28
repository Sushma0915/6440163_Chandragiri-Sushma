// Using Runnable interface
class MessagePrinter implements Runnable {
    private String message;

    public MessagePrinter(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        // Print the message 5 times
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + message + " (" + i + ")");
            try {
                Thread.sleep(500); // Sleep for half a second
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted.");
            }
        }
    }
}

public class ThreadExample {
    public static void main(String[] args) {
        // Create two Runnable tasks
        Runnable task1 = new MessagePrinter("Hello from Thread 1");
        Runnable task2 = new MessagePrinter("Hello from Thread 2");

        // Create and start threads
        Thread thread1 = new Thread(task1, "Thread-1");
        Thread thread2 = new Thread(task2, "Thread-2");

        thread1.start();
        thread2.start();
    }
}