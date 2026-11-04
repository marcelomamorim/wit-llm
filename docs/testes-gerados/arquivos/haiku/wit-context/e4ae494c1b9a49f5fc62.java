/*
 * @test
 * @summary Witup-generated behavioral tests for ConcurrentLinkedDeque.removeFirstOccurrence
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testNullArgumentThrowsNPE();
        testEmptyDequeReturnsFalse();
        testRemovesOnlyFirstOccurrence();
        testAbsentElementReturnsFalseAndKeepsContent();
        testUsesEqualsNotIdentity();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullArgumentThrowsNPE() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        boolean thrown = false;
        try {
            deque.removeFirstOccurrence(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeFirstOccurrence(null) must throw NullPointerException");
        check(deque.size() == 1, "deque must be unchanged after NPE");
        check("a".equals(deque.peekFirst()), "element must remain after NPE");
    }

    static void testEmptyDequeReturnsFalse() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        check(!deque.removeFirstOccurrence("x"), "empty deque must return false");
        check(deque.isEmpty(), "empty deque must stay empty");
    }

    static void testRemovesOnlyFirstOccurrence() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("a");
        check(deque.removeFirstOccurrence("a"), "first occurrence must be removed");
        check(deque.size() == 2, "only one element must be removed");
        check("b".equals(deque.peekFirst()), "head must be b after removal");
        check(deque.removeFirstOccurrence("a"), "second occurrence must be removable");
        check(deque.size() == 1, "size must be 1 after second removal");
        check("b".equals(deque.peekFirst()), "remaining element must be b");
    }

    static void testAbsentElementReturnsFalseAndKeepsContent() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        check(!deque.removeFirstOccurrence("z"), "absent element must return false");
        check(deque.size() == 2, "size must be unchanged when element absent");
        check(deque.contains("a") && deque.contains("b"), "contents must be unchanged");
    }

    static void testUsesEqualsNotIdentity() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("k");
        check(deque.removeFirstOccurrence(new String("k")), "equal but non-identical element must be removed");
        check(deque.isEmpty(), "deque must be empty after removing equal element");
    }
}
