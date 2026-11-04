/*
 * @test
 * @summary Witup-derived tests for LinkedBlockingQueue spliterator tryAdvance behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {
    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testEmptyQueueReturnsFalse();
        testIteratesElementsInFifoOrder();
        testExhaustedSpliteratorReturnsFalse();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNpe() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        Spliterator<String> spliterator = queue.spliterator();
        try {
            spliterator.tryAdvance(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptyQueueReturnsFalse() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Spliterator<String> spliterator = queue.spliterator();
        List<String> sink = new ArrayList<>();
        check(!spliterator.tryAdvance(sink::add), "tryAdvance on empty queue must return false");
        check(sink.isEmpty(), "action must not be invoked on empty queue");
    }

    static void testIteratesElementsInFifoOrder() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("a");
        queue.add("b");
        queue.add("c");
        Spliterator<String> spliterator = queue.spliterator();
        List<String> sink = new ArrayList<>();
        check(spliterator.tryAdvance(sink::add), "first tryAdvance must return true");
        check(spliterator.tryAdvance(sink::add), "second tryAdvance must return true");
        check(spliterator.tryAdvance(sink::add), "third tryAdvance must return true");
        check(!spliterator.tryAdvance(sink::add), "fourth tryAdvance must return false");
        check(sink.size() == 3, "expected 3 elements, got " + sink.size());
        check(sink.get(0).equals("a"), "expected a first");
        check(sink.get(1).equals("b"), "expected b second");
        check(sink.get(2).equals("c"), "expected c third");
    }

    static void testExhaustedSpliteratorReturnsFalse() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("x");
        Spliterator<String> spliterator = queue.spliterator();
        List<String> sink = new ArrayList<>();
        check(spliterator.tryAdvance(sink::add), "first tryAdvance must return true");
        check(!spliterator.tryAdvance(sink::add), "tryAdvance after last element must return false");
        check(!spliterator.tryAdvance(sink::add), "repeated tryAdvance after exhaustion must return false");
        check(sink.size() == 1, "expected exactly 1 element, got " + sink.size());
    }
}
