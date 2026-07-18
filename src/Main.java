import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        ExecutorService executor =
                Executors.newFixedThreadPool(3);

        executor.submit(() ->
                System.out.println(
                        Thread.currentThread().getName()));

        executor.submit(() ->
                System.out.println(
                        Thread.currentThread().getName()));

        executor.submit(() ->
                System.out.println(
                        Thread.currentThread().getName()));

        executor.shutdown();
    }
}