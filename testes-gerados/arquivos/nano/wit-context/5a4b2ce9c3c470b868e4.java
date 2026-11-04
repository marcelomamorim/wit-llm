/*
 * @test
 * @summary Tests the iterator() method of ScheduledThreadPoolExecutor.
 * @run main ScheduledThreadPoolExecutorWitupTest
 */

import java.util.Iterator;
import java.util.concurrent.ReentrantLock;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ScheduledThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testIteratorReturnsExpected();
    }

    public static void testIteratorReturnsExpected() {
        // Setup: create an instance with corePoolSize=1
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        // Access the internal queue via reflection or assume default state
        // Since the queue is private, we test the iterator behavior on an empty executor
        Iterator<Runnable> it = executor.iterator();
        // The iterator should be non-null
        if (it == null) {
            throw new AssertionError("Iterator should not be null");
        }
        // Since no tasks are scheduled, hasNext() should be false
        if (it.hasNext()) {
            throw new AssertionError("Expected no elements in iterator");
        }
        // Add a task to the executor
        executor.execute(() -> {});
        // Obtain a new iterator after adding a task
        Iterator<Runnable> it2 = executor.iterator();
        // The iterator should have at least one element
        if (!it2.hasNext()) {
            throw new AssertionError("Expected at least one element after adding a task");
        }
        // Consume the iterator
        while (it2.hasNext()) {
            Runnable r = it2.next();
            if (r == null) {
                throw new AssertionError("Runnable should not be null");
            }
        }
        // Cleanup
        executor.shutdown();
    }
}
