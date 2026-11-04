/* @test    @summary Test the toString() method of ThreadPoolExecutor for correct output.    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testToStringOutput();
    }

    public static void testToStringOutput() {
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 10, TimeUnit.SECONDS, queue);
        String result = executor.toString();
        boolean containsRunState = result.contains("Run" ) || result.contains("Terminated") || result.contains("Shutting down");
        boolean containsPoolSize = result.contains("pool size = 1") || result.contains("pool size = 2");
        boolean containsActiveThreads = result.contains("active threads = ");
        boolean containsQueuedTasks = result.contains("queued tasks = ");
        boolean containsCompletedTasks = result.contains("completed tasks = ");
        if (!containsRunState) {
            throw new AssertionError("toString() output does not contain run state info: " + result);
        }
        if (!containsPoolSize) {
            throw new AssertionError("toString() output does not contain pool size info: " + result);
        }
        if (!containsActiveThreads) {
            throw new AssertionError("toString() output does not contain active threads info: " + result);
        }
        if (!containsQueuedTasks) {
            throw new AssertionError("toString() output does not contain queued tasks info: " + result);
        }
        if (!containsCompletedTasks) {
            throw new AssertionError("toString() output does not contain completed tasks info: " + result);
        }
        System.out.println("toString() output: " + result);
        executor.shutdown();
    }
}
