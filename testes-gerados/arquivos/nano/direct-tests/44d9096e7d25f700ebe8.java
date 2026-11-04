/*
 * @test    @summary Test the size() method of ScheduledThreadPoolExecutor
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testSizeMethod();
    }

    public static void testSizeMethod() {
        int corePoolSize = 2;
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(corePoolSize);
        try {
            int size = executor.size();
            if (size != corePoolSize) {
                throw new AssertionError("Expected size: " + corePoolSize + ", but got: " + size);
            }
        } finally {
            executor.shutdown();
        }
    }
}
