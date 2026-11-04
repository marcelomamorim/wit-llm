/* @test    @summary Test Executors.newFixedThreadPool with valid and invalid inputs    @run main ExecutorsWitupTest */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testValidThreadPool();
        testZeroThreads();
        testNegativeThreads();
    }

    static void testValidThreadPool() {
        ExecutorService executor = null;
        try {
            executor = Executors.newFixedThreadPool(2);
            boolean submitted = false;
            try {
                executor.submit(() -> System.out.println("Test task"));
                submitted = true;
            } catch (Exception e) {
                submitted = false;
            }
            assert submitted : "Submitting task should succeed with valid thread pool";
        } finally {
            if (executor != null) {
                executor.shutdown();
                try {
                    executor.awaitTermination(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    static void testZeroThreads() {
        boolean exceptionThrown = false;
        try {
            Executors.newFixedThreadPool(0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Creating thread pool with zero threads should throw IllegalArgumentException";
    }

    static void testNegativeThreads() {
        boolean exceptionThrown = false;
        try {
            Executors.newFixedThreadPool(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Creating thread pool with negative threads should throw IllegalArgumentException";
    }
}
