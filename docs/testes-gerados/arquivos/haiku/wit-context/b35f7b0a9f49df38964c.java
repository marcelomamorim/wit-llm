/*
 * @test
 * @summary WITUP-derived checks for ConcurrentLinkedDeque spliterator forEachRemaining
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNullPointerException();
        testForEachRemainingVisitsElementsInOrder();
        testSecondForEachRemainingVisitsNothing();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNullPointerException() {
        ConcurrentLinkedDeque<String> deque =
            new ConcurrentLinkedDeque<>(Arrays.asList("a", "b"));
        Spliterator<String> spliterator = deque.spliterator();
        boolean thrown = false;
        try {
            spliterator.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) must throw NullPointerException");
    }

    static void testForEachRemainingVisitsElementsInOrder() {
        ConcurrentLinkedDeque<String> deque =
            new ConcurrentLinkedDeque<>(Arrays.asList("a", "b", "c"));
        Spliterator<String> spliterator = deque.spliterator();
        List<String> seen = new ArrayList<>();
        spliterator.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("a", "b", "c")),
              "forEachRemaining must visit elements in order, got " + seen);
    }

    static void testSecondForEachRemainingVisitsNothing() {
        ConcurrentLinkedDeque<String> deque =
            new ConcurrentLinkedDeque<>(Arrays.asList("x", "y"));
        Spliterator<String> spliterator = deque.spliterator();
        List<String> seen = new ArrayList<>();
        spliterator.forEachRemaining(seen::add);
        spliterator.forEachRemaining(seen::add);
        check(seen.equals(Arrays.asList("x", "y")),
              "a exhausted spliterator must not revisit elements, got " + seen);
    }
}
