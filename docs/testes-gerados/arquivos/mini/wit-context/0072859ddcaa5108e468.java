/*
 * @test
 * @summary Test ArrayBlockingQueue.removeIf(Predicate) for NPE and UOE
 * @run main java.util.concurrent.ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Predicate;
import java.util.Objects;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfUnsupportedOperation();
    }

    private static void testRemoveIfNullPredicate() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        try {
            queue.removeIf(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRemoveIfUnsupportedOperation() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
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
