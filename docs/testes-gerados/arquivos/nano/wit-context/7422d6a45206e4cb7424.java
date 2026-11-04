/*
 * @test
 * @summary Test for ArrayDeque.forEach method, covering null action and concurrent modification scenarios
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.function.Consumer;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachConcurrentModification();
    }

    private static void testForEachWithNullAction() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        boolean exceptionThrown = false;
        try {
            deque.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testForEachConcurrentModification() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                if (t == 1) {
                    deque.add(3); // Modifying deque during iteration
                }
            }
        };
        boolean exceptionThrown = false;
        try {
            deque.forEach(consumer);
        } catch (java.util.ConcurrentModificationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected ConcurrentModificationException during forEach when deque is modified");
        }
    }
}
