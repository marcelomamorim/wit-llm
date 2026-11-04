/* @test    @summary Test setThreadFactory method of ThreadPoolExecutor    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testSetThreadFactoryWithValidFactory();
        testSetThreadFactoryWithNull();
    }

    static void testSetThreadFactoryWithValidFactory() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, null, null);
        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };
        executor.setThreadFactory(factory);
        // No exception means success
    }

    static void testSetThreadFactoryWithNull() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, null, null);
        try {
            executor.setThreadFactory(null);
            // Expect NullPointerException
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
