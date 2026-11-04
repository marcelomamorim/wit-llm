/*
 * @test
 * @summary Test newCachedThreadPool(ThreadFactory) for basic behavior and argument validation
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        testNewCachedThreadPoolWithValidThreadFactory();
        testNewCachedThreadPoolWithNullThreadFactory();
    }

    private static void testNewCachedThreadPoolWithValidThreadFactory() throws Exception {
        ThreadFactory tf = new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        };

        ExecutorService es = Executors.newCachedThreadPool(tf);
        if (es == null) {
            throw new AssertionError("newCachedThreadPool returned null");
        }

        // Submit a simple task and verify it runs
        final boolean[] ran = {false};
        es.submit(new Runnable() {
            public void run() {
                ran[0] = true;
            }
        }).get(); // wait for completion

        if (!ran[0]) {
            throw new AssertionError("Task did not run in newCachedThreadPool");
        }

        es.shutdown();
        if (!es.awaitTermination(5, TimeUnit.SECONDS)) {
            throw new AssertionError("ExecutorService did not terminate in time");
        }
    }

    private static void testNewCachedThreadPoolWithNullThreadFactory() {
        // The method does not explicitly document throwing NPE, but passing null ThreadFactory
        // will cause NullPointerException in ThreadPoolExecutor constructor.
        // We test that behavior is consistent (throws NPE).
        try {
            Executors.newCachedThreadPool(null);
            throw new AssertionError("Expected NullPointerException when passing null ThreadFactory");
        } catch (NullPointerException expected) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
