import java.util.concurrent.*;
import java.util.*;

public class ExecutorCallableExample {
    public static void main(String[] args) throws Exception {
        // Create a fixed thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // List to hold Future results
        List<Future<String>> futures = new ArrayList<>();

        // Submit Callable tasks
        for (int i = 1; i <= 5; i++) {
            int taskId = i;
            Callable<String> task = () -> {
                Thread.sleep(1000); // Simulate work
                return "Result from task " + taskId;
            };
            futures.add(executor.submit(task));
        }

        // Collect and print results
        for (Future<String> future : futures) {
            String result = future.get();  // Waits for task to complete
            System.out.println(result);
        }

        // Shutdown the executor
        executor.shutdown();
    }
}+