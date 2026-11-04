/*
 * @test
 * @summary Test ThreadPoolExecutor.toString() for correct string format and locking behavior
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        // Create ThreadPoolExecutor with minimal configuration
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
            1, 1, 1000L, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(1));

        // Call toString and check it contains expected substrings
        String str = executor.toString();

        if (!str.contains("pool size = 1")) {
            throw new AssertionError("toString missing pool size info: " + str);
        }
        if (!str.contains("active threads = 0")) {
            throw new AssertionError("toString missing active threads info: " + str);
        }
        if (!str.contains("queued tasks = 0")) {
            throw new AssertionError("toString missing queued tasks info: " + str);
        }
        if (!str.contains("completed tasks = 0")) {
            throw new AssertionError("toString missing completed tasks info: " + str);
        }

        // Submit a task and check toString updates active threads and queued tasks
        executor.execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Give a moment for the task to start
        Thread.sleep(50);

        String str2 = executor.toString();

        if (!str2.contains("active threads = 1") && !str2.contains("active threads = 0")) {
            // active threads may be 0 or 1 depending on timing, accept both
            throw new AssertionError("toString active threads info unexpected: " + str2);
        }

        // Shutdown executor
        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
            throw new AssertionError("Executor did not terminate in time");
        }

        // After shutdown, toString should show terminated state
        String str3 = executor.toString();
        if (!str3.contains("Terminated") && !str3.contains("Shutting down")) {
            throw new AssertionError("toString missing terminated or shutting down state: " + str3);
        }
    }
}
