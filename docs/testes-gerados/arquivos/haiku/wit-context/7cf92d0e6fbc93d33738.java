/*
 * @test
 * @summary Behavioral tests for LinkedBlockingDeque.removeFirstOccurrence with null, missing, and duplicate elements
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testNullArgumentReturnsFalse();
        testNullArgumentDoesNotModifyDeque();
        testRemovesExistingElement();
        testMissingElementReturnsFalse();
        testRemovesOnlyFirstOccurrence();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullArgumentReturnsFalse() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        check(!deque.removeFirstOccurrence(null), "removeFirstOccurrence(null) should return false");
    }

    static void testNullArgumentDoesNotModifyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        deque.removeFirstOccurrence(null);
        check(deque.size() == 2, "deque size should be unchanged after null removal");
        check("a".equals(deque.peekFirst()), "first element should be unchanged");
        check("b".equals(deque.peekLast()), "last element should be unchanged");
    }

    static void testRemovesExistingElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("x");
        deque.add("y");
        check(deque.removeFirstOccurrence("x"), "existing element should be removed");
        check(!deque.contains("x"), "removed element should no longer be contained");
        check(deque.size() == 1, "size should decrease by one");
    }

    static void testMissingElementReturnsFalse() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        check(!deque.removeFirstOccurrence("missing"), "absent element should yield false");
        check(deque.size() == 1, "size should be unchanged when element is absent");
    }

    static void testRemovesOnlyFirstOccurrence() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("a");
        check(deque.removeFirstOccurrence("a"), "first occurrence should be removed");
        check(deque.size() == 2, "only one occurrence should be removed");
        check("b".equals(deque.peekFirst()), "remaining first element should be b");
        check("a".equals(deque.peekLast()), "second occurrence of a should remain last");
    }
}
