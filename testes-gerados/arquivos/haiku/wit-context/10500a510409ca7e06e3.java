/*
 * @test
 * @summary Witup-generated tests for ArrayDeque.offerLast
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testOfferLastAddsAtTail();
        testOfferLastNullThrowsNPE();
        testOfferLastPreservesOrder();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new AssertionError(msg);
        }
    }

    static void testOfferLastAddsAtTail() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        check(d.offerLast(1) == true, "offerLast must return true");
        check(d.size() == 1, "size must be 1 after one offerLast");
        check(d.peekLast() == 1, "element must be at tail");
        check(d.peekFirst() == 1, "single element must also be head");
    }

    static void testOfferLastNullThrowsNPE() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        d.offerLast(7);
        boolean thrown = false;
        try {
            d.offerLast(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "offerLast(null) must throw NullPointerException");
        check(d.size() == 1, "deque must be unchanged after rejected null");
        check(d.peekLast() == 7, "tail must be unchanged after rejected null");
    }

    static void testOfferLastPreservesOrder() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            check(d.offerLast(i) == true, "offerLast must return true");
        }
        check(d.size() == 100, "size must be 100");
        check(d.peekFirst() == 0, "head must be first inserted");
        check(d.peekLast() == 99, "tail must be last inserted");
        int expected = 0;
        for (Integer v : d) {
            check(v == expected, "iteration order must match insertion order");
            expected++;
        }
    }
}
