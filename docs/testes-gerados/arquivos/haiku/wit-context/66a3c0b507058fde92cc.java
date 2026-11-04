/*
 * @test
 * @summary Behavioral checks for LinkedBlockingDeque.removeLastOccurrence
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws Exception {
        testNullArgumentReturnsFalse();
        testEmptyDequeReturnsFalse();
        testRemovesSingleMatch();
        testRemovesLastOccurrenceOnly();
        testAbsentElementLeavesDequeUnchanged();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullArgumentReturnsFalse() {
        LinkedBlockingDeque<String> dq = new LinkedBlockingDeque<>();
        dq.add("a");
        dq.add("b");
        check(!dq.removeLastOccurrence(null), "null argument must return false");
        check(dq.size() == 2, "deque size must not change for null argument");
        check(Arrays.equals(dq.toArray(), new Object[]{"a", "b"}),
              "deque contents must not change for null argument");
    }

    static void testEmptyDequeReturnsFalse() {
        LinkedBlockingDeque<String> dq = new LinkedBlockingDeque<>();
        check(!dq.removeLastOccurrence("a"), "empty deque must return false");
        check(dq.isEmpty(), "empty deque must remain empty");
    }

    static void testRemovesSingleMatch() {
        LinkedBlockingDeque<String> dq = new LinkedBlockingDeque<>();
        dq.add("x");
        check(dq.removeLastOccurrence("x"), "present element must be removed");
        check(dq.isEmpty(), "deque must be empty after removing only element");
    }

    static void testRemovesLastOccurrenceOnly() {
        LinkedBlockingDeque<String> dq = new LinkedBlockingDeque<>();
        dq.add("a");
        dq.add("b");
        dq.add("a");
        dq.add("c");
        check(dq.removeLastOccurrence("a"), "first removal of duplicate must succeed");
        check(Arrays.equals(dq.toArray(), new Object[]{"a", "b", "c"}),
              "only the last occurrence must be removed");
        check(dq.removeLastOccurrence(new String("a")), "equals-based removal must match a distinct equal instance");
        check(Arrays.equals(dq.toArray(), new Object[]{"b", "c"}),
              "remaining elements must keep their order");
        check(!dq.removeLastOccurrence("a"), "no further occurrence must yield false");
        check(dq.size() == 2, "size must reflect removals");
    }

    static void testAbsentElementLeavesDequeUnchanged() {
        LinkedBlockingDeque<String> dq = new LinkedBlockingDeque<>();
        dq.add("p");
        dq.add("q");
        check(!dq.removeLastOccurrence("z"), "absent element must return false");
        check(Arrays.equals(dq.toArray(), new Object[]{"p", "q"}),
              "deque must be unchanged when element is absent");
    }
}
