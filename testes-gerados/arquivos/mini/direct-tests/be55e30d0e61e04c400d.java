/*
 * @test
 * @summary Test ThreadPoolExecutor.toString() for consistent output reflecting state
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) throws Exception {
        // Create ThreadPoolExecutor with core=1, max=1, queue capacity=1
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(1, 1, 1000L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1));

        // toString should contain "Running" initially
        String s = tpe.toString();
        if (!s.contains("Running")) {
            throw new AssertionError("toString() should contain 'Running' when executor is running: " + s);
        }

        // Submit a task that sleeps briefly
        tpe.execute(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
            }
        });

        // toString should reflect active threads >= 1
        s = tpe.toString();
        if (!s.contains("active threads = 1") && !s.contains("active threads = 0")) {
            // active threads may be 0 or 1 depending on timing, accept both
            throw new AssertionError("toString() should show active threads 0 or 1: " + s);
        }

        // Shutdown and await termination
        tpe.shutdown();
        if (!tpe.awaitTermination(2, TimeUnit.SECONDS)) {
            throw new AssertionError("Executor did not terminate in time");
        }

        // After termination, toString should contain "Terminated"
        s = tpe.toString();
        if (!s.contains("Terminated")) {
            throw new AssertionError("toString() should contain 'Terminated' after shutdown: " + s);
        }

        // Check that pool size, queued tasks, and completed tasks appear in toString
        if (!s.contains("pool size =") || !s.contains("queued tasks =") || !s.contains("completed tasks =")) {
            throw new AssertionError("toString() missing expected fields: " + s);
        }
    }
}
