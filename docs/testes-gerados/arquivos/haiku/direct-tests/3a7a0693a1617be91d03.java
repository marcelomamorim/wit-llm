/*
 * @test
 * @summary Verifies ConcurrentLinkedDeque spliterator tryAdvance: null action rejection, empty deque, ordering and end-of-traversal behavior
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testNullActionThrows();
        testEmptyDequeReturnsFalse();
        testAdvanceOrderAndEnd();
        testSingleElement();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrows() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        Spliterator<String> s = deque.spliterator();
        try {
            s.tryAdvance(null);
            throw new RuntimeException("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testEmptyDequeReturnsFalse() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        Spliterator<String> s = deque.spliterator();
        List<String> collected = new ArrayList<>();
        boolean result = s.tryAdvance(x -> collected.add(x));
        check(!result, "tryAdvance on empty deque should return false");
        check(collected.isEmpty(), "action must not be invoked on empty deque");
    }

    static void testAdvanceOrderAndEnd() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        Spliterator<String> s = deque.spliterator();
        List<String> collected = new ArrayList<>();
        check(s.tryAdvance(x -> collected.add(x)), "first tryAdvance should return true");
        check(s.tryAdvance(x -> collected.add(x)), "second tryAdvance should return true");
        check(s.tryAdvance(x -> collected.add(x)), "third tryAdvance should return true");
        check(!s.tryAdvance(x -> collected.add(x)), "tryAdvance past end should return false");
        check(collected.size() == 3, "expected 3 elements, got " + collected.size());
        check(collected.get(0).equals("a"), "element 0 should be a");
        check(collected.get(1).equals("b"), "element 1 should be b");
        check(collected.get(2).equals("c"), "element 2 should be c");
    }

    static void testSingleElement() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("x");
        Spliterator<String> s = deque.spliterator();
        List<String> collected = new ArrayList<>();
        check(s.tryAdvance(x -> collected.add(x)), "single element tryAdvance should return true");
        check(collected.size() == 1 && collected.get(0).equals("x"), "single element should be delivered once");
        check(!s.tryAdvance(x -> collected.add(x)), "second tryAdvance should return false");
        check(collected.size() == 1, "action must not be invoked after end");
    }
}
