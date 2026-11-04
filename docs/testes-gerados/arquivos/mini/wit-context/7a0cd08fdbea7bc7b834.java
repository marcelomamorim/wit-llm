/*
 * @test
 * @summary Test newFixedThreadPool(int, ThreadFactory) for valid and invalid inputs
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        testNewFixedThreadPoolValid();
        testNewFixedThreadPoolInvalid();
    }

    static void testNewFixedThreadPoolValid() throws Exception {
        ThreadFactory tf = new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        };

        ExecutorService es = Executors.newFixedThreadPool(1, tf);
        try {
            // Submit a simple task and check it runs
            final boolean[] ran = {false};
            es.submit(new Runnable() {
                public void run() {
                    ran[0] = true;
                }
            }).get();

            if (!ran[0]) {
                throw new RuntimeException("Task did not run");
            }

            // Check that the executor is not shutdown initially
            if (es.isShutdown()) {
                throw new RuntimeException("ExecutorService should not be shutdown initially");
            }

        } finally {
            es.shutdown();
            if (!es.awaitTermination(5, TimeUnit.SECONDS)) {
                throw new RuntimeException("ExecutorService did not terminate in time");
            }
        }
    }

    static void testNewFixedThreadPoolInvalid() {
        ThreadFactory tf = new ThreadFactory() {
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };

        // According to source, newFixedThreadPool does not explicitly throw IllegalArgumentException for nThreads <= 0
        // but ThreadPoolExecutor constructor does. So test that passing 0 or negative throws IllegalArgumentException.

        int[] invalidValues = {0, -1, -10};
        for (int n : invalidValues) {
            try {
                Executors.newFixedThreadPool(n, tf);
                throw new RuntimeException("Expected IllegalArgumentException for nThreads=" + n);
            } catch (IllegalArgumentException expected) {
                // expected
            } catch (Exception e) {
                throw new RuntimeException("Unexpected exception for nThreads=" + n + ": " + e);
            }
        }
    }
}
