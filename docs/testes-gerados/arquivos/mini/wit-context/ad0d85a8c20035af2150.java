/*
 * @test
 * @summary Test PriorityBlockingQueue.removeIf(Predicate) for NPE and UnsupportedOperationException
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Predicate;
import java.util.Objects;

public class PriorityBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfUnsupportedOperation();
    }

    static void testRemoveIfNullPredicate() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        try {
            queue.removeIf(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    static void testRemoveIfUnsupportedOperation() {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>();
        // The WIT expath suggests bulkRemove throws UnsupportedOperationException
        // The current code calls bulkRemove(filter) which may throw UnsupportedOperationException
        // We test that removeIf propagates this exception when filter != null
        Predicate<Integer> alwaysTrue = new Predicate<Integer>() {
            public boolean test(Integer i) { return true; }
        };
        try {
            queue.removeIf(alwaysTrue);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
