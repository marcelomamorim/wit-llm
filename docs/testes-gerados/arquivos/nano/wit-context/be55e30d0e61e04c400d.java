/* @test    @summary Test ThreadPoolExecutor.toString() for normal behavior    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testToStringRunning();
        testToStringTerminated();
        testToStringShuttingDown();
    }

    static void testToStringRunning() {
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS, queue);
        String result = executor.toString();
        if (result == null || !result.contains("Running")) {
            throw new AssertionError("Expected 'Running' in toString output, got: " + result);
        }
        executor.shutdown();
    }

    static void testToStringTerminated() {
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, queue);
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // ignore
        }
        String result = executor.toString();
        if (result == null || !result.contains("Terminated")) {
            throw new AssertionError("Expected 'Terminated' in toString output, got: " + result);
        }
    }

    static void testToStringShuttingDown() {
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS, queue);
        executor.shutdown();
        String result = executor.toString();
        if (result == null || !result.contains("Shutting down")) {
            throw new AssertionError("Expected 'Shutting down' in toString output, got: " + result);
        }
    }
}
