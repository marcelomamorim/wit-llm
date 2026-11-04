/*
 * @test
 * @summary Test ScheduledThreadPoolExecutor remove() method behavior
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.RejectedExecutionHandler;

public class ScheduledThreadPoolExecutorWitupTest {

    // Minimal subclass to expose remove() method for testing
    static class TestScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {
        // Expose remove() method publicly for testing
        public void testRemove() {
            super.remove();
        }

        // Constructor chaining
        TestScheduledThreadPoolExecutor(int corePoolSize) {
            super(corePoolSize);
        }
    }

    public static void main(String[] args) {
        testRemoveThrowsIllegalStateException();
    }

    private static void testRemoveThrowsIllegalStateException() {
        TestScheduledThreadPoolExecutor executor = new TestScheduledThreadPoolExecutor(1);

        boolean threw = false;
        try {
            executor.testRemove();
        } catch (IllegalStateException e) {
            threw = true;
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception", t);
        }

        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException was not thrown by remove()");
        }
    }
}
