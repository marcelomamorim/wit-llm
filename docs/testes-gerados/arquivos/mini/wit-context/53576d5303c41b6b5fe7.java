/*
 * @test
 * @summary Test ThreadPoolExecutor.setThreadFactory with null and valid inputs
 * @run main ThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorWitupTest {
    public static void main(String[] args) {
        testSetThreadFactoryNull();
        testSetThreadFactoryValid();
    }

    private static void testSetThreadFactoryNull() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 1, 1L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1));
        try {
            executor.setThreadFactory(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            executor.shutdownNow();
        }
    }

    private static void testSetThreadFactoryValid() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1, 1, 1L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1));
        ThreadFactory tf = new ThreadFactory() {
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };
        executor.setThreadFactory(tf);
        executor.shutdownNow();
    }
}
