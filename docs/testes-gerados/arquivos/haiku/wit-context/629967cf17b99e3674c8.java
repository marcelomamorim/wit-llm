/*
 * @test
 * @summary Witup-generated checks for ConcurrentLinkedQueue spliterator tryAdvance
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
        testCollectionConstructorOrder();
    }

    static void testNullActionThrowsNpe() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("a");
        Spliterator<String> s = q.spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testEmptyQueueReturnsFalseAndDoesNotInvokeAction() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        Spliterator<String> s = q.spliterator();
        List<String> sink = new ArrayList<>();
        boolean result = s.tryAdvance(x -> sink.add(x));
        check(!result, "tryAdvance on empty queue must return false");
        check(sink.isEmpty(), "action must not be invoked on empty queue");
    }

    static void testConsumesElementsInFifoOrder() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("a");
        q.add("b");
        q.add("c");
        Spliterator<String> s = q.spliterator();
        List<String> sink = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            check(s.tryAdvance(x -> sink.add(x)), "tryAdvance should return true while elements remain");
        }
        check(sink.equals(Arrays.asList("a", "b", "c")),
              "elements must be delivered in FIFO order, got " + sink);
        check(!s.tryAdvance(x -> sink.add(x)), "tryAdvance must return false after exhaustion");
        check(sink.size() == 3, "no extra element must be delivered after exhaustion");
    }

    static void testCollectionConstructorOrder() {
        ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<>(Arrays.asList(1, 2, 3));
        Spliterator<Integer> s = q.spliterator();
        List<Integer> sink = new ArrayList<>();
        check(s.tryAdvance(x -> sink.add(x)), "tryAdvance should consume first element");
        check(sink.equals(Arrays.asList(1)), "first delivered element must be 1, got " + sink);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
