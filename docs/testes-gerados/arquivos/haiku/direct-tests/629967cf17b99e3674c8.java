/*
 * @test
 * @summary Witup-derived checks for ConcurrentLinkedQueue spliterator tryAdvance
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testEmptyQueueReturnsFalseAndDoesNotInvokeAction();
        testConsumesElementsInFifoOrder();
        testSingleElementThenExhausted();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNpe() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("a");
        Spliterator<String> s = q.spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "tryAdvance(null) must throw NullPointerException");
    }

    static void testEmptyQueueReturnsFalseAndDoesNotInvokeAction() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        Spliterator<String> s = q.spliterator();
        List<String> calls = new ArrayList<>();
        boolean result = s.tryAdvance(x -> calls.add(x));
        check(!result, "tryAdvance on empty queue must return false");
        check(calls.isEmpty(), "action must not be invoked on empty queue");
    }

    static void testConsumesElementsInFifoOrder() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("a");
        q.add("b");
        q.add("c");
        Spliterator<String> s = q.spliterator();
        List<String> out = new ArrayList<>();
        while (s.tryAdvance(x -> out.add(x))) {
            // keep advancing
        }
        check(out.equals(Arrays.asList("a", "b", "c")),
              "tryAdvance must yield elements in FIFO order, got " + out);
    }

    static void testSingleElementThenExhausted() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("only");
        Spliterator<String> s = q.spliterator();
        List<String> calls = new ArrayList<>();
        check(s.tryAdvance(x -> calls.add(x)), "first tryAdvance must return true");
        check(calls.equals(Arrays.asList("only")), "first action call must receive the element");
        boolean second = s.tryAdvance(x -> calls.add(x));
        check(!second, "second tryAdvance must return false after exhaustion");
        check(calls.size() == 1, "action must not be invoked after exhaustion");
    }
}
