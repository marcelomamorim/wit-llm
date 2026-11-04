/*
 * @test
 * @summary Tests ConcurrentLinkedDeque.removeFirstOccurrence(Object) for null rejection, empty and absent elements, first-occurrence removal and equals-based matching
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testNullRejected();
        testEmptyReturnsFalse();
        testAbsentReturnsFalse();
        testRemovesFirstOccurrenceOnly();
        testRemovesHead();
        testRemovesTail();
        testUsesEquals();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullRejected() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("a");
        boolean thrown = false;
        try {
            d.removeFirstOccurrence(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null argument");
        check(d.toString().equals("[a]"), "deque must be unchanged after null argument");
    }

    static void testEmptyReturnsFalse() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        check(!d.removeFirstOccurrence("a"), "empty deque must return false");
        check(d.isEmpty(), "empty deque must remain empty");
    }

    static void testAbsentReturnsFalse() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("a");
        d.add("b");
        check(!d.removeFirstOccurrence("z"), "absent element must return false");
        check(d.toString().equals("[a, b]"), "deque must be unchanged when element absent");
    }

    static void testRemovesFirstOccurrenceOnly() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("a");
        d.add("b");
        d.add("a");
        check(d.removeFirstOccurrence("a"), "existing element must return true");
        check(d.toString().equals("[b, a]"), "only first occurrence must be removed, got " + d);
    }

    static void testRemovesHead() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("a");
        d.add("b");
        check(d.removeFirstOccurrence("a"), "head element must be removed");
        check(d.toString().equals("[b]"), "unexpected deque after head removal, got " + d);
    }

    static void testRemovesTail() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("a");
        d.add("b");
        check(d.removeFirstOccurrence("b"), "tail element must be removed");
        check(d.toString().equals("[a]"), "unexpected deque after tail removal, got " + d);
    }

    static void testUsesEquals() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("x");
        check(d.removeFirstOccurrence(new String("x")), "equal (not identical) element must be removed");
        check(d.isEmpty(), "deque must be empty after removing equal element");
    }
}
