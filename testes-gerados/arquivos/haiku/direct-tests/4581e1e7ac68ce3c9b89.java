/*
 * @test
 * @summary ArrayDeque.add(E) appends the element at the tail, returns true, and rejects null
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.Iterator;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testReturnsTrueAndAppendsAtTail();
        testPreservesInsertionOrder();
        testManyAddsGrowArray();
        testNullElementIsRejected();
    }

    static void testReturnsTrueAndAppendsAtTail() {
        ArrayDeque<String> d = new ArrayDeque<>();
        boolean result = d.add("a");
        if (!result) {
            throw new AssertionError("add must return true");
        }
        if (d.size() != 1) {
            throw new AssertionError("size expected 1 but was " + d.size());
        }
        if (!"a".equals(d.peekLast())) {
            throw new AssertionError("tail expected a but was " + d.peekLast());
        }
        if (!"a".equals(d.peekFirst())) {
            throw new AssertionError("head expected a but was " + d.peekFirst());
        }
    }

    static void testPreservesInsertionOrder() {
        ArrayDeque<Integer> d = new ArrayDeque<>();
        if (!d.add(1) || !d.add(2) || !d.add(3)) {
            throw new AssertionError("add must return true for each element");
        }
        if (d.size() != 3) {
            throw new AssertionError("size expected 3 but was " + d.size());
        }
        if (d.peekFirst() != 1) {
            throw new AssertionError("head expected 1 but was " + d.peekFirst());
        }
        if (d.peekLast() != 3) {
            throw new AssertionError("tail expected 3 but was " + d.peekLast());
        }
        Iterator<Integer> it = d.iterator();
        int expected = 1;
        while (it.hasNext()) {
            int v = it.next();
            if (v != expected) {
                throw new AssertionError("iteration expected " + expected + " but was " + v);
            }
            expected++;
        }
        if (expected != 4) {
            throw new AssertionError("iteration count mismatch");
        }
    }

    static void testManyAddsGrowArray() {
        ArrayDeque<Integer> d = new ArrayDeque<>(1);
        final int n = 1000;
        for (int i = 0; i < n; i++) {
            if (!d.add(i)) {
                throw new AssertionError("add returned false at " + i);
            }
        }
        if (d.size() != n) {
            throw new AssertionError("size expected " + n + " but was " + d.size());
        }
        if (d.peekFirst() != 0) {
            throw new AssertionError("head expected 0 but was " + d.peekFirst());
        }
        if (d.peekLast() != n - 1) {
            throw new AssertionError("tail expected " + (n - 1) + " but was " + d.peekLast());
        }
    }

    static void testNullElementIsRejected() {
        ArrayDeque<String> d = new ArrayDeque<>();
        try {
            d.add(null);
            throw new AssertionError("add(null) must throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected: null elements are prohibited
        }
        if (!d.isEmpty()) {
            throw new AssertionError("deque must remain empty after rejected null");
        }
    }
}
