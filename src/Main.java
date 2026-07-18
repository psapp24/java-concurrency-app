import java.util.concurrent.*;

public class Main {

    public static void main(String[] args)
            throws Exception {

        ExecutorService executor =
                Executors.newFixedThreadPool(2);

        Callable<Integer> task = () -> {

            Thread.sleep(3000);

            return 100;
        };

        Future<Integer> future =
                executor.submit(task);

        System.out.println("Task Submitted");

        Integer result = future.get();

        System.out.println(result);

        executor.shutdown();
    }
}