/* @test    @summary Test ScheduledThreadPoolExecutor.remove method for correctness and exception safety    @run main ScheduledThreadPoolExecutorWitupTest */

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testRemoveReturnsTrueWhenObjectExists();
        testRemoveReturnsFalseWhenObjectDoesNotExist();
        testRemoveWithNullObject();
    }

    private static void testRemoveReturnsTrueWhenObjectExists() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Runnable task = () -> {};
        Object taskWrapper = new Object();
        // Add task to executor queue
        executor.execute(task);
        // Attempt to remove the task
        boolean result = executor.remove(task);
        // The remove should return true if task was in queue
        if (!result) {
            throw new AssertionError("Expected remove to return true for existing task");
        }
        // Cleanup
        executor.shutdown();
    }

    private static void testRemoveReturnsFalseWhenObjectDoesNotExist() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Object nonExistentObject = new Object();
        boolean result = executor.remove(nonExistentObject);
        if (result) {
            throw new AssertionError("Expected remove to return false for non-existent object");
        }
        executor.shutdown();
    }

    private static void testRemoveWithNullObject() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        boolean result = false;
        try {
            result = executor.remove(null);
        } catch (Exception e) {
            throw new AssertionError("remove(null) should not throw exception", e);
        }
        if (result) {
            throw new AssertionError("Expected remove(null) to return false");
        }
        executor.shutdown();
    }
}
