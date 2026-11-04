/*
 * @test
 * @summary Test java.util.concurrent.Executors.newCachedThreadPool(ThreadFactory) basic behavior
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a simple ThreadFactory that creates threads with a known name prefix
        ThreadFactory tf = new ThreadFactory() {
            private int count = 0;
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "TestThread-" + count++);
                t.setDaemon(false);
                return t;
            }
        };

        ExecutorService es = Executors.newCachedThreadPool(tf);

        // Submit a simple task and verify it runs
        Runnable task = new Runnable() {
            public void run() {
                // no-op
            }
        };

        es.execute(task);

        // Shutdown and await termination
        es.shutdown();
        boolean terminated = es.awaitTermination(5, TimeUnit.SECONDS);

        if (!terminated) {
            throw new RuntimeException("ExecutorService did not terminate in time");
        }

        // Verify that the thread factory was used by submitting a task that records thread name
        final String[] threadName = new String[1];
        ExecutorService es2 = Executors.newCachedThreadPool(tf);
        es2.execute(new Runnable() {
            public void run() {
                threadName[0] = Thread.currentThread().getName();
            }
        });
        es2.shutdown();
        es2.awaitTermination(5, TimeUnit.SECONDS);

        if (threadName[0] == null || !threadName[0].startsWith("TestThread-")) {
            throw new RuntimeException("ThreadFactory was not used to create thread with expected name prefix");
        }
    }
}
