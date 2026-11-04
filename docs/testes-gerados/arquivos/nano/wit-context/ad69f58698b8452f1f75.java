/*
 * @test
 * @summary Test for ConcurrentLinkedDeque.removeIf method, focusing on null argument handling.
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Predicate;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testRemoveIfNullPredicate();
        testRemoveIfWithValidPredicate();
    }

    private static void testRemoveIfNullPredicate() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.removeIf(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to removeIf");
        }
    }

    private static void testRemoveIfWithValidPredicate() {
        ConcurrentLinkedDeque<Integer> deque = new ConcurrentLinkedDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Predicate<Integer> predicate = new Predicate<Integer>() {
            @Override
            public boolean test(Integer e) {
                return e % 2 == 0;
            }
        };
        boolean result = deque.removeIf(predicate);
        if (result != true) {
            throw new AssertionError("removeIf should return true when elements are removed");
        }
        if (deque.contains(2)) {
            throw new AssertionError("Element 2 should have been removed");
        }
        if (deque.size() != 2) {
            throw new AssertionError("Deque size should be 2 after removal");
        }
    }
}
