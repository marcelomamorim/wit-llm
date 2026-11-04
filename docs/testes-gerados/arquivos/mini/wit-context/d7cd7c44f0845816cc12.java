/*
 * @test
 * @summary Test LinkedBlockingQueue.removeIf(Predicate) for NPE and UOE
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;
import java.util.Objects;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfUnsupportedOperation();
    }

    private static void testRemoveIfNullPredicate() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        try {
            queue.removeIf(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveIfUnsupportedOperation() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        // The bulkRemove method is called by removeIf and throws UnsupportedOperationException
        // according to WIT expath and source code context.
        // We test that removeIf with a non-null predicate throws UOE.
        Predicate<String> alwaysTrue = new Predicate<String>() {
            public boolean test(String s) {
                return true;
            }
        };
        try {
            queue.removeIf(alwaysTrue);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
