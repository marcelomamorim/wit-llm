/*
 * @test
 * @summary Test for ScheduledThreadPoolExecutor.size() method.
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testSizeMethod();
    }

    public static void testSizeMethod() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        int size = executor.size();
        if (size != 0) {
            System.out.println("Test failed: Expected size 0, but got " + size);
        } else {
            System.out.println("Test passed: size() returns 0 as expected.");
        }
        // Additional test: after submitting a task, size should remain unchanged as size() returns the number of scheduled tasks.
        // Since no tasks are scheduled yet, size should be 0.
        // We do not submit tasks here to keep the test simple and deterministic.
    }
}
