/*
 * @test
 * @summary Tests ConcurrentLinkedDeque.removeLastOccurrence observable behavior
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testNullThrowsAndLeavesDequeUnchanged();
        testRemovesLastOccurrenceAmongDuplicates();
        testAbsentElementReturnsFalse();
        testEmptyDequeReturnsFalse();
        testUsesEqualsNotIdentity();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullThrowsAndLeavesDequeUnchanged() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("a");
        boolean thrown = false;
        try {
            d.removeLastOccurrence(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null argument");
        check(d.size() == 1, "deque size changed after null argument");
        check(d.peekFirst().equals("a"), "deque content changed after null argument");
    }

    static void testRemovesLastOccurrenceAmongDuplicates() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("a");
        d.add("b");
        d.add("a");
        d.add("c");
        check(d.removeLastOccurrence("a"), "expected true when element is present");
        List<String> actual = new ArrayList<>(d);
        check(actual.equals(Arrays.asList("a", "b", "c")),
              "expected only last occurrence removed, got " + actual);
        check(d.size() == 3, "unexpected size after removal");
    }

    static void testAbsentElementReturnsFalse() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("a");
        d.add("b");
        check(!d.removeLastOccurrence("z"), "expected false for absent element");
        List<String> actual = new ArrayList<>(d);
        check(actual.equals(Arrays.asList("a", "b")), "deque changed after absent removal");
    }

    static void testEmptyDequeReturnsFalse() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        check(!d.removeLastOccurrence("a"), "expected false on empty deque");
        check(d.isEmpty(), "empty deque should remain empty");
    }

    static void testUsesEqualsNotIdentity() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("x");
        check(d.removeLastOccurrence(new String("x")), "expected removal by equals");
        check(d.isEmpty(), "deque should be empty after equal-element removal");
    }
}
