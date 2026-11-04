/*
 * @test
 * @summary Verifies ArrayDeque.offerFirst rejects null elements and inserts non-null elements at the front
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testOfferFirstNullThrowsAndLeavesDequeUnchanged();
        testOfferFirstNonNullReturnsTrueAndInsertsAtFront();
        testOfferFirstOnEmptyDeque();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testOfferFirstNullThrowsAndLeavesDequeUnchanged() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        boolean thrown = false;
        try {
            deque.offerFirst(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "offerFirst(null) should throw NullPointerException");
        check(deque.size() == 1, "deque size should be unchanged after failed offerFirst(null)");
        check("a".equals(deque.peekFirst()), "deque head should be unchanged after failed offerFirst(null)");
    }

    static void testOfferFirstNonNullReturnsTrueAndInsertsAtFront() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("b");
        deque.add("c");
        boolean result = deque.offerFirst("a");
        check(result, "offerFirst should return true");
        check(deque.size() == 3, "size should be 3 after offerFirst");
        check("a".equals(deque.peekFirst()), "offerFirst should insert at the front");
        check("c".equals(deque.peekLast()), "tail should remain the last inserted element");
    }

    static void testOfferFirstOnEmptyDeque() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        boolean result = deque.offerFirst(Integer.valueOf(42));
        check(result, "offerFirst on empty deque should return true");
        check(deque.size() == 1, "size should be 1 after offerFirst on empty deque");
        check(deque.peekFirst().intValue() == 42, "element should be retrievable from the front");
        check(deque.peekLast().intValue() == 42, "element should also be the tail");
    }
}
