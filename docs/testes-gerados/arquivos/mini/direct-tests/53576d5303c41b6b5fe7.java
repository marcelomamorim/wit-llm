/*
 * @test
 * @summary Test ThreadPoolExecutor.setThreadFactory behavior
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        testSetThreadFactoryNull();
        testSetThreadFactoryValid();
    }

    static void testSetThreadFactoryNull() {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1));
        try {
            tpe.setThreadFactory(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            tpe.shutdown();
        }
    }

    static void testSetThreadFactoryValid() {
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1));
        ThreadFactory tf = new ThreadFactory() {
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };
        tpe.setThreadFactory(tf);
        // No direct getter, so test by submitting a task and checking it runs
        final boolean[] ran = {false};
        Runnable task = new Runnable() {
            public void run() {
                ran[0] = true;
            }
        };
        tpe.execute(task);
        tpe.shutdown();
        try {
            if (!tpe.awaitTermination(2, TimeUnit.SECONDS)) {
                throw new Error("Executor did not terminate in time");
            }
        } catch (InterruptedException e) {
            throw new Error("Interrupted while waiting for termination");
        }
        if (!ran[0]) {
            throw new Error("Task did not run, possible ThreadFactory issue");
        }
    }
}
