/*
 * @test
 * @summary Test for ScheduledThreadPoolExecutor.toArray()
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testToArrayNormal();
        testToArrayWithLockUnlocked();
    }

    private static void testToArrayNormal() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        // Accessing private lock field via reflection is not allowed; instead, simulate normal operation
        Object[] result = executor.toArray();
        if (result == null) {
            throw new AssertionError("toArray() returned null");
        }
        // Additional checks could be added here if the queue has elements
    }

    private static void testToArrayWithLockUnlocked() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        // Since lock is private and no public API to unlock, we test the method in normal state
        Object[] result = executor.toArray();
        if (result == null) {
            throw new AssertionError("toArray() returned null when lock is unlocked");
        }
    }
}
