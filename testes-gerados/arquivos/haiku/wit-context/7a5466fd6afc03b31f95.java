/*
 * @test
 * @summary Verifies submit(Callable) rejects a null task with NullPointerException and returns a usable Future for a valid task
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) throws Exception {
        testNullTaskThrowsNPE();
        testValidCallableReturnsResult();
    }

    static void testNullTaskThrowsNPE() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            Callable<Object> nullTask = null;
            try {
                executor.submit(nullTask);
                throw new AssertionError("Expected NullPointerException for null task");
            } catch (NullPointerException expected) {
            }
        } finally {
            executor.shutdownNow();
        }
    }

    static void testValidCallableReturnsResult() throws Exception {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        try {
            Callable<Integer> task = new Callable<Integer>() {
                public Integer call() {
                    return 42;
                }
            };
            Future<Integer> future = executor.submit(task);
            if (future == null) {
                throw new AssertionError("submit returned null Future");
            }
            Integer result = future.get();
            if (result.intValue() != 42) {
                throw new AssertionError("Unexpected result: " + result);
            }
        } finally {
            executor.shutdownNow();
        }
    }
}
