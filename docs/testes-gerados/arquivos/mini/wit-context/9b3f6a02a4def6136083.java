/*
 * @test
 * @summary Test LinkedBlockingDeque.removeIf(Predicate) for null argument and behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Predicate;
import java.util.Objects;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfUnsupportedOperation();
    }

    private static void testRemoveIfNullPredicate() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.removeIf(null);
            throw new AssertionError("Expected NullPointerException for null predicate");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testRemoveIfUnsupportedOperation() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        // According to current implementation, bulkRemove throws UnsupportedOperationException
        // So removeIf should propagate it
        Predicate<String> alwaysTrue = new Predicate<String>() {
            public boolean test(String s) {
                return true;
            }
        };
        try {
            deque.removeIf(alwaysTrue);
            throw new AssertionError("Expected UnsupportedOperationException from removeIf");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
